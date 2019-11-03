package ir.heydarii.imagefinder.pojos

/**
 * Image search main response
 */
data class ImageSearchResponseModel(
    val data: List<Data>,
    val page: Int,
    val per_page: Int,
    val search_id: String,
    val total_count: Int
)

/**
 * Main data of each image
 */
data class Data(
    val assets: Assets,
    val description: String
)

/**
 * Assets model that contains image url
 */
data class Assets(
    val preview: Preview
)

/**
 * Main Image address model
 */
data class Preview(
    val url: String
)