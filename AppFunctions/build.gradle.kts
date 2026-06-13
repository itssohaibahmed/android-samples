// =============================================================================
// Root project — plugin aliases (apply false; consumed by modules)
// =============================================================================

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
}
