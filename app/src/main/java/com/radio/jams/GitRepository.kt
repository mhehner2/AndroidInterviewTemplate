package com.radio.jams

import kotlinx.serialization.Serializable

@Serializable
data class GitRepository(
    val id: Long,
    val full_name: String,
)