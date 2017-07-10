package com.example.android.project4.models

data class ValueItem(
	val datePublished: String? = null,
	val image: Image? = null,
	val provider: List<ProviderItem?>? = null,
	val name: String? = null,
	val about: List<AboutItem?>? = null,
	val description: String? = null,
	val category: String? = null,
	val url: String? = null
)
