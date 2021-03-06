package com.example;

/**
 * Created by gifo on 12.04.2018.
 */

public class Student {
    String name = "";
    public Student(String t) {
        this.name = t;
    }
}

/*
* Сетевое взаимодействие
* Объявить пермишены - 1 обращение к сети, 2 - состояние получать
* UI - главный поток в андроиде, с 3 версы андроиды все сетевые операции по умолчанию запрещены в главном потоке
* Отрисовка ui - нельзя заниматся в побочных потоках
*
* делаем запрос
* получаем ответ
* запись ответа в БД
* перерисовка по данным из БД после обновления
*
* Почитать про LiveData - сразу после обновления БД авто запускаем перерисовку (почитать про Room)
* почитать про библиотеку okHttp
*
* Postman - позволяет тестировать сетевые интерфейсы - с помощью неё можно выполнить запросы
* и получить результат (get/post запросы - url/response body)
*
* Дви библы для работы с картинками Picasso / Glide, гугл рекомендует глайд,
 * позволяет взять юрл и показать результат в имидж вью, глайд позволяет масштабировать картинку под вью и её кешировать
 *
* */