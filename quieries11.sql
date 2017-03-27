# Запит: Вивести всі екзамени, які проводить заданий викладач

SELECT *
FROM exam
WHERE professor_id IN (SELECT id
                       FROM teacher
                       WHERE name = "Teachername1")


# Запит: Вивести екзамени, які організовує задана кафедра

SELECT *
FROM exam
WHERE course_name IN (SELECT name
                      FROM course
                      WHERE department_name = "Літературознавства");

# Запит: Вивести екзамени, які складає задана група

SELECT *
FROM exam
WHERE course_name AND group_year AND professor_id IN (SELECT course_name AND year AND professor_id
Вивести екзамени, які організовує задана кафедра                                                      FROM tgroup
                                                      WHERE id = "01");

# Запит: Вивести екзамени, які проводяться у вказану дату

SELECT *
FROM exam
WHERE date = "01.01.2017";

# Запит: Для заданого іспиту вивести к-сть студентів із заданим балом

SELECT COUNT(DISTINCT student_id)
FROM result
WHERE mark = "61" AND group_id IN (SELECT id		
  						                     FROM tgroup
  						                     WHERE year AND course_name IN (SELECT group_year AND course_name
  						           	                                        FROM exam
  						           	                                        WHERE course_name = "БМТ" AND group_year = "2017"));

# Запит: Для заданого іспиту вивести к-сть неявок

SELECT COUNT(DISTINCT student_id)
FROM result
WHERE mark = "ABSENT" AND group_id IN (SELECT id		
  						                         FROM tgroup
  						                         WHERE year AND course_name IN (SELECT group_year AND course_name
  						           	                                            FROM exam
  						           	                                            WHERE course_name = "БМТ" AND group_year = "2017"));

# Запит: Для заданого іспиту вивести середній бал студентів

SELECT AVG(mark)
FROM result
WHERE group_id IN (SELECT id		
  				         FROM tgroup
  				         WHERE year AND course_name IN (SELECT group_year AND course_name
  						           	                        FROM exam
  						           	                        WHERE course_name = "БМТ" AND group_year = "2017"));

#  Запит: Для заданого предмету з екзаменів вивести студентів, які слухають дисципліну

SELECT name
FROM student
WHERE id IN (SELECT student_id
             FROM result
             WHERE group_id IN (SELECT id
             	                  FROM tgroup
             	                  WHERE course_name = "БМТ"))
                                
                                
                                                     Індивідуальний звіт
    На другу ітерацію переді мною було поставлено задачу написання наступних запитів до використовуваної бази даних:
1. Вивести всі екзамени, які проводить заданий викладач
2. Вивести екзамени, які організовує задана кафедра
3. Вивести екзамени, які складає задана група
4. Вивести екзамени, які проводяться у вказану дату
5. Для заданого іспиту вивести кількість студентів із заданим балом
6. Для заданого іспиту вивести кількість неявок
7. Для заданого іспиту вивести середній бал студентів
8. Для заданого предмету з екзаменів вивести студентів, які слухають дисципліну

Вищевказані запити були описані за допомогою мови структурованих запитів SQL (structured query language). Код запитів
міститься у вихідному файлі quieries11.sql.  
