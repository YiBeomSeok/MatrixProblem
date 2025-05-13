package matrix.rotate

import com.bmsk.matrix.matrix.rotate.RotateLandmarks
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertTrue

class RotateLandmarksBulkTest {
    // “눈, 코, 윗입술, 아랫입술” 총 5개 랜드마크 예시
    private val landmarks = listOf(
        150f to 120f,   // 왼쪽 눈
        490f to 120f,   // 오른쪽 눈
        320f to 240f,   // 코
        320f to 280f,   // 윗입술
        320f to 320f    // 아랫입술
    )

    // 50장의 640×480 이미지
    private val imageSizes = List(10_000) { 640 to 480 }

    @Test
    fun `fast version still passes for 50 images`() {
        val totalTime = measureTimeMillis {
            for ((w, h) in imageSizes) {
                RotateLandmarks.rotateLandmarks(landmarks, w, h, 270)
            }
        }

        assertTrue(totalTime < 1000, "Fast version too slow: took $totalTime ms for ${imageSizes.size} images")
    }

    @Test
    fun `slow version fails for 50 images`() {
        val totalTime = measureTimeMillis {
            for ((w, h) in imageSizes) {
                RotateLandmarks.rotateLandmarksSlow(landmarks, w, h, 270)
            }
        }

        assertTrue(totalTime < 1000, "Performance failure: took $totalTime ms for ${imageSizes.size} images")
    }
}