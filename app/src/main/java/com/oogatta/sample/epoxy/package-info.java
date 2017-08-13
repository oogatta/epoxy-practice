@EpoxyDataBindingLayouts({R.layout.title, R.layout.feed, R.layout.footer})
@PackageModelViewConfig(rClass = R.class)
@PackageEpoxyConfig(
        requireAbstractModels = true,
        requireHashCode = true,
        implicitlyAddAutoModels = true
)
package com.oogatta.sample.epoxy;

import com.airbnb.epoxy.EpoxyDataBindingLayouts;
import com.airbnb.epoxy.PackageModelViewConfig;
import com.airbnb.epoxy.PackageEpoxyConfig;