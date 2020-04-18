package com.example.fazyksiezyca

class Settings {
    var hemiSphere: String? = null
    var algorithm: String? = null

    constructor() {}

    constructor(hemiSphere: String?, algorithm: String?) {
        this.hemiSphere = hemiSphere
        this.algorithm = algorithm
    }

    constructor(line: String?) {
        if (line != null) {
            val tokens = line.split(";")
            if (tokens.size == 2) {
                hemiSphere = tokens[0]
                algorithm = tokens[1]
            }
        }
    }

    fun toCSV(): String {
        return "$hemiSphere;$algorithm\n"
    }
}