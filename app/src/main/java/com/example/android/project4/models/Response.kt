package com.example.android.project4.models

data class Response(
	val readLink: String? = null,
	val totalEstimatedMatches: Int? = null,
	val type: String? = null,
	val sort: List<SortItem?>? = null,
	val value: List<ValueItem?>? = null
)
