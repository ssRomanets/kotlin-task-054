import kotlinx.coroutines.flow.*

suspend fun main() {
    //1
    var numbersList = listOf(1,2,3,4,5).asFlow()
    val reducedValue = numbersList.map{ n -> n*n}.reduce{a, b -> a+b}
    println(reducedValue)

    //2
    while (true) {
        println("Введите метку имени человека: \n")
        val first = readLine().toString()

        println("Введите возраст человека: \n")
        val age = readLine().toString().toInt()

        getPerson(first, age)

        println("Продожить программу? 1.Да, 2.Нет \n")
        if (readLine().toString() =="2")
        {
            break;
        }
    }

    //3
    val namesFlow = listOf("Петр","Николай","Василий").asFlow()
    val cartsFlow = listOf("2981 4664 2788 5634", "7728 4346 5068 1464", "9456 7443 9129 6753").asFlow()
    val passwordsFlow = listOf(7828, 8333, 3092).asFlow()

    var resultList : MutableList<String> = mutableListOf()
    val resultsFlow = display(namesFlow, cartsFlow, passwordsFlow)
    resultsFlow.collect({person -> resultList.addLast("Person(${person.toString()})")})
    println("Выводим результат финальной задачи: ")
    println(resultList.toString())
}

suspend fun getPerson(first: String, age: Int)
{
    personsFlow.filter { person ->
        person.name.contains(first) && person.age  == age
    }
        .collect{ person ->
            println("Имя: ${person.name}; возраст: ${person.age}")
        }
}

data class Person2(val name: String, val age: Int)

val personsFlow = listOf(
    Person2("Сергей", 24),
    Person2("Анна",   18),
    Person2("Борис",  27),
    Person2("Николай", 34),
    Person2("Петр",   38),
    Person2("Иван",  47),
    Person2("Михаил", 33),
    Person2("Дмитрий",   29),
    Person2("Екатерина",  37),
    Person2("Валерия",  19)
).asFlow()

data class Person3(val name: String, val cart: String, val password: Int) {
    override fun toString(): String {
        return "name=${name}, cart=${cart} , password=${password.toInt()} "
    }
}


suspend fun <T1,T2,T3 >display(namesFlow: Flow<T1>, cartsFlow: Flow<T2>, passwordsFlow: Flow<T3>): Flow<Person3> {
    val flowNamesCarts = namesFlow.zip(cartsFlow) {name, cart -> name.toString() + "," + cart.toString() };

    return flowNamesCarts.zip(passwordsFlow) { dataNameCart, password ->
            Person3(
                dataNameCart.split(",").get(0),
                dataNameCart.split(",").get(1),
                password.toString().toInt()
            )
    }
}