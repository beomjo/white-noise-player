/*
 * Designed and developed by 2021 beomjo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beomjo.whitenoise.repositories.home

import com.beomjo.whitenoise.model.Category
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : HomeRepository {
    override suspend fun getHomeCategoryList(): List<Category> {
        return firestore.collection("home")
            .document("category")
            .collection("categoryIds")
            .get()
            .await()
            .documents
            .map { it["ref"] }
            .map { it as DocumentReference }
            .map { it.get().await() }
            .map { it.toObject(Category::class.java)!! }.toList()
    }
}
