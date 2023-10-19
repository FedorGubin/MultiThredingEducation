package com.example.multithredingeducation

import java.time.Year

open class Second

class First : Second(), FirstInterface, SecondInterface {
    val test: TestEnum = TestEnum.SHOW_CONTENT

    // в чём отличие sealed class от enum?
    // enum знает количество своих инстансов
    // sealed знает количество наследников
    fun test(input2: TestEnum, input: TestSealedClass) {
        when (input2) {
            TestEnum.LOADING -> {

            }
            TestEnum.EMPTY_CONTENT -> {

            }
            TestEnum.SHOW_CONTENT -> {

            }
            TestEnum.ERROR -> {

            }
            TestEnum.NEW -> {

            }
        }

        when (input) {
            is TestSealedClass.Test -> {}
            is TestSealedClass.Test1 -> {}
            is TestSealedClass.Test2 -> {}
        }
    }
}

sealed class TestSealedClass {
    class Test : TestSealedClass()
    class Test2 : TestSealedClass()
    class Test1 : TestSealedClass()
}

sealed interface TestSealedInterface {
    class Test : TestSealedInterface
    class Test2 : TestSealedInterface
    class Test1 : TestSealedInterface
}

enum class TestEnum(str: String) {
    LOADING("ljrnkf"), SHOW_CONTENT("ljrnkf"), EMPTY_CONTENT("ljrnkf"), ERROR("ljrnkf"), NEW("ljrnkf")
}

interface FirstInterface

interface SecondInterface





// в чем разница между ними
// 1. абстрактный класс может иметь состояние в отличие от интерфейса

abstract class FirstAbst {

    open val testValueClass: String = "klwefj"

    fun testClass1() {

    }

    abstract fun testClass()
}

interface FirstInter {

    val testValueInterface: String
    fun testInterface()

    fun testInterface1() {

    }
}

class Test : FirstAbst(), FirstInter, FirstInterface, SecondInterface {

    override fun testClass() {

    }

    override val testValueInterface: String = "erjklfn"

    override fun testInterface() {
        TestNew("lkwjesfn").hashCode()
        TestNew("lkwjesfn").hashCode()

        val result: Boolean = TestNew("kwejrfn") == TestNew("kwejrfn")
    }
}










// зачем?
// что бы автоматически создались методы hasCode и equals (and toString and copy)
data class TestNew(
    val kjwefn: String,
    val test3: Int = 4
)











