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

package com.beomjo.whitenoise.repositories.auth

import android.content.Intent
import com.beomjo.whitenoise.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun isLoggedIn(): Boolean

    fun getGoogleSinInIntent(): Intent

    fun getIdTokenFromIntent(intent: Intent): Flow<String?>

    fun getGoogleCredential(idToken: String?): Flow<AuthCredential>

    fun loginWithCredential(credential: AuthCredential): Flow<AuthResult>

    fun getUserInfo(): User
}
