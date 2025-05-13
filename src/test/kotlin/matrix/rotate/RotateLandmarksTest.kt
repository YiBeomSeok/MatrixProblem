package matrix.rotate

import com.bmsk.matrix.matrix.rotate.RotateLandmarks
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertTrue

class RotateLandmarksBulkTest {
    private val landmarks = listOf(
        150f to 120f,
        490f to 120f,
        320f to 240f,
        320f to 280f,
        320f to 320f
    )

    @Test
    fun `fast version still passes for 10_000 images`() {
        val imageSizes = List(10_000) { 640 to 480 }

        val totalTime = measureTimeMillis {
            for ((w, h) in imageSizes) {
                RotateLandmarks.rotateLandmarks(landmarks, w, h, 270)
            }
        }

        assertTrue(totalTime < 1000, "Fast version too slow: took $totalTime ms for ${imageSizes.size} images")
    }

    @Test
    fun `slow version fails for 10_000 images`() {
        val imageSizes = List(10_000) { 640 to 480 }

        val totalTime = measureTimeMillis {
            for ((w, h) in imageSizes) {
                RotateLandmarks.rotateLandmarksSlow(landmarks, w, h, 270)
            }
        }

        assertTrue(totalTime < 1000, "Performance failure: took $totalTime ms for ${imageSizes.size} images")
    }
}