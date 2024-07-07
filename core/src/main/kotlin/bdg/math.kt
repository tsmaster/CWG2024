package bdg

class Math {

    companion object {
        public fun mapval(inVal: Float, inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
            val t:Float = (inVal - inMin) / (inMax - inMin)

            return t * (outMax - outMin) + outMin
        }

        fun radiansToDegrees(radians: Float): Float {
            return mapval(radians, 0f, 2 * java.lang.Math.PI.toFloat(), 0.0f, 360.0f);
        }

        fun degreesToRadians(degrees: Float): Float {
            return mapval(degrees, 0f, 360.0f, 0.0f, 2 * java.lang.Math.PI.toFloat());
        }

        // wraps the value so that the out lies between +m and -m
        fun wrap(inVal: Float, m: Float): Float {
            var mutVal = inVal

            while (mutVal > m) {
                mutVal -= 2*m
            }

            while (mutVal < -m) {
                mutVal += 2*m
            }

            return mutVal
        }

        fun clamp(inVal: Float, minVal: Float, maxVal: Float): Float {
            if (inVal < minVal)
                return minVal
            if (inVal > maxVal)
                return maxVal
            return inVal
        }


        const val PI_f = java.lang.Math.PI.toFloat()

        const val TAU_f = 2 * PI_f
    }
}
