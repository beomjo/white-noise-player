package com.beomjo.whitenoise.utilities.ext

import android.support.v4.media.session.PlaybackStateCompat

inline val PlaybackStateCompat.isPlay
    get() = (state == PlaybackStateCompat.STATE_PLAYING) &&
            (state != PlaybackStateCompat.STATE_PAUSED) &&
            (state != PlaybackStateCompat.STATE_STOPPED)