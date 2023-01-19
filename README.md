### Приложение на java для парсинга большого excel файла(более 500 000 строк) и сохранения его в БД postgreSql. 

#### Структура файла:
 
id, name, last name, birthday, company, position at work, salary 

#### Валидация: 
1. id, name, last name – обязательные поля 
2. birthday – на момент запуска должен быть старше 18 лет 
3. id – уникальное поле 
4. company – завести таблицу компаний в БД и проверять данные на вхождение в эту таблицу 
5. position at work – аналогично company 


#### Ограничения:
использование оперативной памяти не более 512Мб, java 8+
 
Результат добавить к себе в GitHub c инструкцией для запуска и работы.