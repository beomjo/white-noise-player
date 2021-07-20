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

package com.beomjo.whitenoise.di

import android.content.ComponentName
import android.content.Context
import android.media.MediaPlayer
import com.beomjo.whitenoise.repositories.player.PlayerRepository
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.ui.player.PlayerService
import com.beomjo.whitenoise.ui.player.PlayerServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MediaPlayerModule {

    @Provides
    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }

    @Singleton
    @Provides
    fun providePlayerManager(
        repository: PlayerRepository,
        connection: PlayerServiceConnection
    ): PlayerManager {
        return PlayerManager(repository, connection)
    }

    @Provides
    fun providePlayerServiceConnection(
        @ApplicationContext context: Context
    ): PlayerServiceConnection {
        return PlayerServiceConnection(context, ComponentName(context, PlayerService::class.java))
    }
}
