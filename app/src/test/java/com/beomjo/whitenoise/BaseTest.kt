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

package com.beomjo.whitenoise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.Called
import io.mockk.InternalPlatformDsl
import io.mockk.MockKException
import org.junit.Rule
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.javaMethod

abstract class BaseTest : MockitoTest() {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    infix fun Any.invoke(name: String) = DynamicCall(this, name)

    infix fun Any.invokeNoArgs(name: String): Any? {
        val method = this.javaClass.getDeclaredMethod(name)
        method.isAccessible = true
        return method.invoke(this)
    }

    class DynamicCall(val self: Any, val methodName: String) {
        companion object {

            /**
             * {object} invoke {"methodName"} withArguments {parameterList}
             */
            infix fun DynamicCall.withArguments(args: List<Any?>): Any? {
                val argArray = args.toTypedArray()
                val params = arrayOf(self, *argArray)
                val func = self::class.functions.firstOrNull {
                    if (it.name != methodName) {
                        return@firstOrNull false
                    }
                    if (it.parameters.size != params.size) {
                        return@firstOrNull false
                    }

                    for ((idx, param) in it.parameters.withIndex()) {
                        val matches = when (val classifier = param.type.classifier) {
                            is KClass<*> -> classifier.isInstance(params[idx])
                            is KTypeParameter -> classifier.upperBounds.anyIsInstance(params[idx])
                            else -> false
                        }
                        if (!matches) {
                            return@firstOrNull false
                        }
                    }

                    return@firstOrNull true
                } ?: run {
                    val errorMsg = "can't find function $methodName(${args.joinToString(", ")}) for dynamic call"
                    throw MockKException(errorMsg)
                }
                func.javaMethod?.let { InternalPlatformDsl.makeAccessible(it) }
                return func.call(*params)
            }

            /**
             * {object} invoke {"methodName"} withArguments Called
             */
            infix fun DynamicCall.withArguments(called: Called): Any? {
                val method = self.javaClass.getDeclaredMethod(methodName)
                method.isAccessible = true
                return method.invoke(self)
            }

            /**
             * {object} invoke {"methodName"} noArguments Called
             */
            infix fun DynamicCall.noArguments(called: Called): Any? {
                val method = self.javaClass.getDeclaredMethod(methodName)
                method.isAccessible = true
                return method.invoke(self)
            }

            private fun List<KType>.anyIsInstance(value: Any?): Boolean {
                return any { bound ->
                    val classifier = bound.classifier
                    if (classifier is KClass<*>) {
                        classifier.isInstance(value)
                    } else {
                        false
                    }
                }
            }
        }
    }
}
