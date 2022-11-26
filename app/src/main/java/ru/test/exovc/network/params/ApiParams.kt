package ru.test.exovc.network.params

data class ApiParams(
    val group: String = "video",
    val categoryId: String = "1"
) {

    fun toMap() = hashMapOf<String, String>().apply {
        put(KEY_GROUP, group)
        put(KEY_CATEGORY_ID, categoryId)
    }

    companion object {
        const val KEY_GROUP = "group"
        const val KEY_CATEGORY_ID = "category_id"
    }
}
