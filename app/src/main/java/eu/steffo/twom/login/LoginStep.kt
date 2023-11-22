package eu.steffo.twom.login

enum class LoginStep(val step: Int) {
    NONE(0),
    SERVICE(1),
    WELLKNOWN(2),
    FLOWS(3),
    WIZARD(4),
    LOGIN(5),
    DONE(6),
}