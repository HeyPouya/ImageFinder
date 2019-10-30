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
        val aspect: Double,
        val assets: Assets,
        val contributor: Contributor,
        val description: String,
        val id: String,
        val image_type: String,
        val media_type: String,
        val url: String
)

/**
 * Assets model that contains image url
 */
data class Assets(
        val huge_thumb: HugeThumb,
        val large_thumb: LargeThumb,
        val preview: Preview,
        val small_thumb: SmallThumb
)

/**
 * HugeThumb model
 */
data class HugeThumb(
        val height: Int,
        val url: String,
        val width: Int
)

/**
 * LargeThumb model
 */
data class LargeThumb(
        val height: Int,
        val url: String,
        val width: Int
)

/**
 * Main Image address model
 */
data class Preview(
        val height: Int,
        val url: String,
        val width: Int
)

/**
 * SmallThumb model
 */
data class SmallThumb(
        val height: Int,
        val url: String,
        val width: Int
)

/**
 * Contributor model
 */
data class Contributor(
        val id: String
)