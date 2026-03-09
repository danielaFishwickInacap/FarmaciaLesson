package com.kotlinpl.farmacialesson.view_model

import com.kotlinpl.farmacialesson.domain.Drugstore

data class UIState(
    val isLoading: Boolean = false,
    val drugstores: List<Drugstore> = emptyList(),
    val error: DrugstoresErrors? = null
)

