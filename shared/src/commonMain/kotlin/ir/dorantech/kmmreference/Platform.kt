package ir.dorantech.kmmreference

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform