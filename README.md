# Mailing
Send messages and collect answers

# Основные цели 
  рассылка сообщений c html-шаблоном  
  установка почтового imap клиента  
  подготовка хранилища для обработки ответов и прикриплений, храниение imap-ответов, парсинг imap на контент ответа и прикриплений к письму  
  обработка и добавление к хранилищу статистики доставки и ответа используя сервис api.sendsay.ru (документация https://sendsay.ru/api/API-0.151-20170517.html)  
  логирования процесса доставки  
  возврат отчета ошибок для недоставленных сообщений в сжатом виде (mail,id ошибки,explanation)
  
# Сборка
  Производится средой NetBeeans. Для удобства оставлены библиотеки в lib
  
# Использование

Весь процесс делится на 4 части. 

  Рассылка. Использовать пакет api. Ключи запуска Messenger даются в следующем порядке
  
      1 - аунтентификация, получение id сессии на несколько часов
      
      2 x - рассылка. x - любое положительное целое число (чаще всего 1) - стартовый рассылочный id. На этом шаге подхватываются данные из mail.txt
      
  Статистика. Использовать пакет api
  
      6 x y - создание json-ответа доставки с полями (mail,deliv.status,deliv.dt). x - см выше. y - временой интервал статистики в днях (положительное число), начало отчета от текущей даты 
      
      7 x y -  создание json-ответа ответа с полями (mail,deliv.status,deliv.dt,response.status,response.dt). x,y - см выше.
      
  Сбор ответов
  
      Имея статистику ответов replyStatus_date , используется класс mailClient.ApiMail
      
      В хранилище попадают ответы и прикрипления, если они еще находятся на почтовом сервере
      
      В любом случае формируются пустые файлы отвечающие полям статистики ответов, вмето поля mail может поставляться id, который берется из ac13.txt
      
    Отчет об ошибках
  
      Имея date.log с помощью bash запустить script c параметром date, выход - errorsLog_date.txt
 
 # Описание ресурсов
 
 Все ресурсы кроме incl.txt и excl.txt, имеют один ключ.
 
    mail.txt - почта
    ac13.txt - id почты
    names.txt - имена для вставки в шаблон, кодируются утилитой native2ascii
    incl.txt - ключи почты, которые входят в рассылку
    excl.txt - ключи почты, которые не входят в рассылку  
      
  
