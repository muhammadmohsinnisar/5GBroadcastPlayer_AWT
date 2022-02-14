package com.example.a5gbroadcastplayer_awt;

import android.content.Context;

import androidx.annotation.Nullable;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetProvider;

/**
 * A wrapper class for a {@link CronetEngine}.
 *
 * @deprecated Use {@link CronetEngine} directly. See the <a
 *     href="https://developer.android.com/guide/topics/connectivity/cronet/start">Android developer
 *     guide</a> to learn how to instantiate a {@link CronetEngine} for use by your application. You
 *     can also use {@link CronetUtil#buildCronetEngine} to build a {@link CronetEngine} suitable
 *     for use with {@link CronetDataSource}.
 */
@Deprecated
public final class CronetEngineWrapper {

    @Nullable
    private final CronetEngine cronetEngine;

    /**
     * Creates a wrapper for a {@link CronetEngine} built using the most suitable {@link
     * CronetProvider}. When natively bundled Cronet and GMSCore Cronet are both available, the
     * natively bundled provider is preferred.
     *
     * @param context A context.
     */
    public CronetEngineWrapper(Context context) {
        this(context, /* userAgent= */ null, /* preferGMSCoreCronet= */ false);
    }

    /**
     * Creates a wrapper for a {@link CronetEngine} built using the most suitable {@link
     * CronetProvider}. When natively bundled Cronet and GMSCore Cronet are both available, {@code
     * preferGMSCoreCronet} determines which is preferred.
     *
     * @param context A context.
     * @param userAgent A default user agent, or {@code null} to use a default user agent of the
     *     {@link CronetEngine}.
     * @param preferGooglePlayServices Whether Cronet from Google Play Services should be preferred
     *     over Cronet Embedded, if both are available.
     */
    public CronetEngineWrapper(
            Context context, @Nullable String userAgent, boolean preferGooglePlayServices) {
        cronetEngine = CronetUtil.buildCronetEngine(context, userAgent, preferGooglePlayServices);
    }

    /**
     * Creates a wrapper for an existing {@link CronetEngine}.
     *
     * @param cronetEngine The CronetEngine to wrap.
     */
    public CronetEngineWrapper(CronetEngine cronetEngine) {
        this.cronetEngine = cronetEngine;
    }

    /**
     * Returns the wrapped {@link CronetEngine}.
     *
     * @return The CronetEngine, or null if no CronetEngine is available.
     */
    @Nullable
    /* package */ CronetEngine getCronetEngine() {
        return cronetEngine;
    }
}

