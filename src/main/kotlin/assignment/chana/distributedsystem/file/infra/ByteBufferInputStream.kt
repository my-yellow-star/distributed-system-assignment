package assignment.chana.distributedsystem.file.infra

import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer


class ByteBufferInputStream(private var mBuffer: ByteBuffer?) : InputStream() {
    override fun close() {
        mBuffer = null
    }

    private fun ensureStreamAvailable() {
        if (mBuffer == null) {
            throw IOException("read on a closed InputStream!")
        }
    }

    override fun read(): Int {
        ensureStreamAvailable()
        return if (mBuffer!!.hasRemaining()) mBuffer!!.get().toInt() and 0xFF else -1
    }

    override fun read(buffer: ByteArray): Int {
        return this.read(buffer, 0, buffer.size)
    }

    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        ensureStreamAvailable()
        if (!(offset >= 0 && length >= 0 && length <= buffer.size - offset)) {
            throw IndexOutOfBoundsException()
        }
        if (length == 0) {
            return 0
        }
        val remainingSize = Math.min(mBuffer!!.remaining(), length)
        if (remainingSize == 0) {
            return -1
        }
        mBuffer!![buffer, offset, remainingSize]
        return remainingSize
    }

    override fun skip(n: Long): Long {
        ensureStreamAvailable()
        if (n <= 0L) {
            return 0L
        }
        val length = n.toInt()
        val remainingSize = Math.min(mBuffer!!.remaining(), length)
        mBuffer!!.position(mBuffer!!.position() + remainingSize)
        return length.toLong()
    }

    override fun available(): Int {
        ensureStreamAvailable()
        return mBuffer!!.remaining()
    }
}