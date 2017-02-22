#Вивести усі іспити
SELECT course_name, group_year, date, professor_id, aud
FROM exam;

#Вивести аудиторії, які за певною датою не мають іспитів
SELECT auditorium.number
FROM auditorium
WHERE number NOT IN (SELECT aud
                     FROM exam
                     WHERE date = '21');

#вибрати exam, де у course outcome = 'zalik'
SELECT course_name, group_year, date, professor_id, aud
FROM exam
WHERE course_name IN (SELECT name
                      FROM course
                      WHERE outcome = 'zalik')