package com.bmsk.matrix.matrix.rotate

object RotateLandmarks {
    /**
     * @param landmarks: 정방향 좌표 리스트 (Pair<x, y>)
     * @param width:  회전된 이미지의 가로 픽셀 수
     * @param height: 회전된 이미지의 세로 픽셀 수
     * @param rotation: 시계 방향 회전 각도 (0, 90, 180, 270)
     * @return 변환된 좌표 리스트
     */
    fun rotateLandmarks(
        landmarks: List<Pair<Float, Float>>,
        width: Int,
        height: Int,
        rotation: Int
    ): List<Pair<Float, Float>> {
        val result = ArrayList<Pair<Float, Float>>(landmarks.size)
        for ((x, y) in landmarks) {
            val xp: Float
            val yp: Float
            when (rotation) {
                0 -> {
                    xp = x
                    yp = y
                }

                90 -> {
                    xp = y
                    yp = (width - 1 - x)
                }

                180 -> {
                    xp = (width - 1 - x)
                    yp = (height - 1 - y)
                }

                270 -> {
                    xp = (height - 1 - y)
                    yp = x
                }

                else -> throw IllegalArgumentException("Unsupported rotation: \$rotation")
            }
            result.add(Pair(xp, yp))
        }
        return result
    }

    fun rotateLandmarksSlow(
        landmarks: List<Pair<Float, Float>>,
        width: Int,
        height: Int,
        rotation: Int
    ): List<Pair<Float, Float>> {
        // 더미 픽셀
        val size = width * height
        val pixels = ByteArray(size)
        val newWidth = if (rotation % 180 == 0) width else height
        val newHeight = if (rotation % 180 == 0) height else width
        val rotated = ByteArray(size)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val srcIdx = y * width + x
                val dstIdx = when (rotation) {
                    0 -> srcIdx
                    90 -> x * newWidth + (newWidth - 1 - y)
                    180 -> (newHeight - 1 - y) * newWidth + (newWidth - 1 - x)
                    270 -> (newHeight - 1 - x) * newWidth + y
                    else -> throw IllegalArgumentException("Unsupported rotation: \$rotation")
                }
                rotated[dstIdx] = pixels[srcIdx]
            }
        }

        // 좌표 변환
        val result = ArrayList<Pair<Float, Float>>(landmarks.size)
        for ((x, y) in landmarks) {
            val xp: Float
            val yp: Float
            when (rotation) {
                0 -> {
                    xp = x
                    yp = y
                }

                90 -> {
                    xp = y
                    yp = (width - 1 - x)
                }

                180 -> {
                    xp = (width - 1 - x)
                    yp = (height - 1 - y)
                }

                270 -> {
                    xp = (height - 1 - y)
                    yp = x
                }

                else -> throw IllegalArgumentException("Unsupported rotation: \$rotation")
            }
            result.add(xp to yp)
        }
        return result
    }
}