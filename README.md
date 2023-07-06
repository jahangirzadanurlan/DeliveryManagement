# Delivery Management
![Untitled Diagram drawio (6)](https://github.com/jahangirzadanurlan/DeliveryManagement/assets/103985861/58c7e799-f4b4-44f9-a92a-1fa2211dd12e)


	
Delivery-management
Building 187C, 115 Heydar Aliyev Ave, Baku 1029  055 032 77 78
deliverymanagement@gmail.com  www.deliverymanagement.com

SCenario

Delivery Management System

Mövcud problemlər

Günümüz də insanlar, bəzi ehtiyacları və ya tələbatları üçün məhsulu ünvanlarına sifariş etmək istəyirlər. Bu zaman onlara ehtiyac olan əsas əlaqə vasitəsi olur. Hər hansı əlaqə vasitəsi tapdığdan sonra sifarişi verib tamamlayır. Amma, müştəri sifarişin hazır olub olmadığını, yola çıxıb çıxmadığını və s. kimi detalları izləyə bilmir. Və bunun üçün müəyyən çətinlik yaşayır.

Problemin həllinin təsviri

 

Çatdırılma proqramı yaradarağ, insanların sifarişini istəyə uyğun nəğd və ya nəğdsiz ödəmə imkanına sahib olur. Və həmçinin sifarişi rahatlığla izləyə bilir. 

Yaradılacağ proyektdə bu problemlər öz həllini tapmalıdır.
Giriş:
⦁	İstifadəçinin register imkanı +
⦁	İstifadəçinin login imkanı
⦁	Forget password-la şifrəni yeniləmək imkanı +

Admin panel:
⦁	Məhsul əlavə etmək(Seçilmiş category-ya)/yeniləmək/silmək/oxumaq(axtarış etmək) – (Bu kategoriya üçün də keçərlidir)
⦁	Sürücü əlavə etmək(yeniləmək, silmək, oxumaq)
⦁	Müştəriləri (silmək, oxumaq)
⦁	Sifarişləri oxumaq
⦁	Günlük, aylıq, illik gəlir statistikası və ümumi qəbul edilən sifarişlər, customer-lər, sürücülər.



Customer panel
⦁	Bütün məhsulları görə bilmək, axtarış imkanı
⦁	Yalnız öz məlumatlarını görə bilməsi
⦁	Məhsulları səbətə əlavə etmək, çıxarmaq və sayını artırmaq.
⦁	Sifarişin toplam məbləği haqqında məlumat əldə etmək.
⦁	Sifarişi tamamlamağ. +
⦁	Sifarişi izləmək. Statusunu öyrənmək. (Yalnız öz sifarişini)

Driver panel
⦁	Öz sifarişlərini görə bilmək
⦁	Sifarişin statusunu dəyişmək

+ yazdığlarım əməliyyatlar-da email notification göndərilməlidir.


Model:

Company – id, name, desc, total_budget – // buna heç bir cədvəl bağlı olmayacaq. Üzərində düşünməyin. Sadəcə gəlirlərin toplanacağı yerdir. Və onu burda saxlayacağıq.
Category – id, name, desc
Food – İd, name, desc, amount, foodDetails, category, image
Role – id, name
User - id, name, surname, birthdate, email, password, phoneNumber, role, isEnabled
Driver – id, isBusy, user
Cart – id, count, total amount, food, user
Order – id, place, cartId, driver(user), status
ConfirmationToken – id, token, createdAt, confirmedAt, confirm, user
ForgetPasswordToken – id, token, createdAt, confirmedAt, confirm, user

Role:
ADMİN, DRİVER, CUSTOMER

Qeyd: Bu cədvəllər arasında ola biləcək əlaqələr, field üzərində tam dəqiq göstərilməyib. Bunları özünüz diqqət yetirin. Və həmçinin lazım ola biləcək bütün fieldlər qeyd olunmayıb. Məsələn, security ilə bağlı olarağ profilin təsdiq olunub olunmaması kimi.


⦁	POST - /customers - İstifadəçi register olur. Unique olarağ, email(username) daxil ediləcək. Şifrə isə minimum 8 simvoldan ibarət olmalı və həmçinin A-Za-z0-9 simvollarını özündə daşımalıdır. Profilinə daxil olunması üçün ilk olaraq, maili təsdiqlənməlidir. 
GET - /user/verification-account - Bunun üçün də mailə link göndəriləcək(register olduğu anda). Və mail-də olan linkə klikləndikdən sonra profilinə daxil ola biləcək. Yəni, register-dən sonra default olarağ user-in accountEnable field-i disable olacağ. Enable olması üçün isə göndərdiyimiz linkə klikləməlidir.

Bunu xüsusi olarağ qeyd edim, yuxarıda emailinə link göndərəcəyiniz endpoint qeyd olunub. Emaili göndərərkən, tokeni generasiya edirsiz(UUİD). Və daha sonra bazaya save edirsiz. Və bu tokeni endpointin parametrinə əlavə edərək, email-ə göndərərsiz. Daha sonra sizin həmin endpointə gələn sorğudan parametri alıb yoxlayırsız. Əgər göndərdiyiniz token bazada varsa və təsdiq olunmayıbsa, təsdiq edirsiz. Yəni confirmedAt təsdiq olunduğu tarixi qeyd edirsiz. Və bununla yanaşı həmin user-ində account-un enable edirsiz. 

Hə birdə bu jwt deyil.

⦁	POST - /user/password-resets - İstifadəçi şifrəsini unudur. Və bunun üçün bu endpointinə müraciət edərək mail göndərir. Və biz də onun mailinə şifrəni yeniləməsi üçün bir link göndəririk. Bu link aşağıdakı endpointdir.
GET - /user/password-resets/validation və email-ə göndərilən linki klikləndikdən sonra 
PUT – /user/password endpointin-ə müraciət edərək, şifrəni dəyişə bilər. Əks halda, access denied xətası alacağ.

Qeyd – Forget password üçün tokeni təsdiqləmə müddəti 15 dəqiqədir. Əgər token generasiya olunduğu vaxtdan 15 dəqiqə sonra linkə click-lənsə artığ o keçərsiz sayılır yəni token expired olur. Və beləcə artığ şifrəni də dəyişə bilməz. Bunun üçün yenə token göndərməlidir.

⦁	GET - /admin/dashboard – admin bura müraciət edərək, aşağıda qeyd etdiyim statistikaları izləyə bilməlidir. (Bu məlumatları bazada saxlamırsız. Sadəcə baza da var olan datalar üzərində sorğular yazıb, onlar üzərində hesablama aparırsız.)
Toplam customer sayı
Toplam driver sayı
Günlük – gəlir, sifariş sayı
Aylıq – gəlir, sifariş sayı
İllik – gəlir, sifariş sayı

⦁	GET – /drivers - ADMİN  - Bütün sürücülər haqqında məlumatı görə bilmək imkanı.
⦁	POST – /drivers - ADMİN – Sürücünü register etmək imkanı. Bu zaman şifrə default olarağ hər hansı bir şey yazıla bilər. Amma, bu əhəmiyyət kəsb etməyəcək. Çünki o, linklə şifrəsini dəyişməli olacağ. Və həmçinin profilinin aktiv olunması da şifrəni təyin etdikdən sonra olacağ. Sürücünü register etdikdən sonra onun emailinə şifrə yeniləməsi ilə bağlı link getməlidir. 
GET - /user/password-resets/validation email-ə göndərilən linki klikləndikdən sonra 
PUT – /user/password endpointin-ə müraciət edərək, şifrəni dəyişir. 
⦁	GET – /drivers/{id} – admin bütün sürücüləri, driver isə sadəcə özünü görmə imkanına sahibdir.
⦁	PUT – /drivers/{id} – admin bütün sürücüləri, driver isə sadəcə öz məlumatlarını update etmə imkanına sahibdir.
⦁	DELETE – /drivers/{id} – admin istənilən sürücüləri silə bilər.
⦁	GET – /customers - Bütün customer-ləri görmə imkanına sahibdir.
⦁	GET – /customers/{id} - admin istənilən müştərini, müştəri isə sadəcə özünü görə bilər.
⦁	DELETE - /customers/{id} -  admin istənilən müştərinin hesabını silə bilər.
⦁	GET – /customers/{id}/cart – customer bütün cart məhsullarını görə bilir və həmçinin məhsulların toplam məbləği və hər məhsulun sayı.
⦁	POST – /customers/{id}/cart – customer cart-a yeni məhsul əlavə edə bilər.
⦁	DELETE – /customers/{id}/cart/{id} – customer cart-dan istənilən məhsulu silə bilir.
⦁	POST – /customers/{id}/cart/checkout – əsas hissələrdən biri də buradır. Customer – lokasiya və kart məlumatlarını doldurub bu endpointi çağıracağ.
Əvvəla qeyd edim ki, kart məlumatları sadəcə simulyasiya üçündür, bu məlumatlarla heç bir şey etməyəcəksiz. Bizə burdan lazım olan lokasiya və həmin user-ə bağlı olan cart məlumatlarını götürüb order hissəsinə əlavə etməkdir. Yəni bu api çağırıldığı anda, məhsullar, userİd və cardİd Order cədvəlinə mənimsədilməlidir. Və həmçinin toplam ödəniş isə şirkətin hesabına keçməlidir. Və hansı driver məşğul deyilsə onlardan birinə bu sifariş verilməlidir. Və proses uğurla bitdikdən sonra məhsullar cart-dan silinməlidir. 
⦁	GET – /orders – Admin bütün sifarişləri görə və onun statusunu izləyə bilər.
⦁	GET - /orders/{id} – Admin istənilən sifarişi, customer və driver isə yalnız özünə aid olan sifarişləri görə bilər.
⦁	PATCH - /orders/{id} – driver sifarişin statusunu dəyişə bilər. Təhvil verdim deyə məsələn. Və bunu dəyişdiyi anda həmçinin məşğul field-i false-a dəyişir.
⦁	GET - /foods – Hər kəs bütün məhsulları görə bilər.
⦁	POST - /foods – Admin yeni məhsul əlavə edə bilər. (məhsulun bir şəkli də olmalıdır. Yəni, şəklində upload edə bilməlidir.)
⦁	GET - /foods/{id} – Hər kəs istənilən məhsulu görə bilər.
⦁	PUT - /foods/{id} – Admin istənilən məhsulu update bilər.
⦁	DELETE - /foods/{id} – Admin istənilən məhsulu silə bilər.
⦁	GET - /categories – Hər kəs bütün categoriyaları görə bilər.
⦁	POST - /categories – Admin yeni category əlavə edə bilər.
⦁	GET - /categories/{id} – Hər kəs istənilən category görə bilər.
⦁	PUT - /categories/{id} – Admin istənilən category update bilər.
⦁	DELETE - /categories/{id} – Admin istənilən category silə bilər.
⦁	GET - /categories/{id} – Hər kəs istənilən category-ə məxsus məhsulları görə bilər.


Endpoints:
User controller - /user
     /password-resets
           POST – forget password or password resets (send link to user email)
           /validation
               GET – validate token
     /password
          PUT – set password 

     /confirmation-account
           GET – verify account after that register for customer (token)

Admin controller - /admin
 /dashboard   
     GET – statistics  - ADMIN

Driver Controller - /drivers
    GET – all drivers - ADMIN
    POST – save driver - ADMIN
          /{id}
              GET – find by id – ADMIN, DRIVER(Just himself/herself)
              PUT – update driver - ADMIN, DRIVER(Just himself/herself)
             DELETE – delete driver – ADMIN

Customer Controller - /customers
    GET – all customers – ADMIN
   POST – save customer  – CUSTOMER
    /{id}
         GET – find by id – ADMIN, CUSTOMER(Just himself/herself)
         DELETE – delete customer – ADMIN
        
         /cart
            GET – all product in cart by customer id – CUSTOMER
             POST – add product to cart  – CUSTOMER
            /id
                  DELETE – remove product from cart – CUSTOMER
           /checkout – (kart məlumatlarını daxil edəcək və ödəniş olunmasını simulyasiya edəcəyik.)
                POST – buy product in cart – CUSTOMER


Order Controller - /orders
    GET – all orders – ADMIN
          /{id}
              GET – find by id – ADMIN, CUSTOMER - DRİVER(Just himself/herself)
              PATCH – update order status – ADMIN, DRİVER(Just himself/herself)
          

Food Controller - /products
    GET – all food – PERMİT ALL
    POST – save food – ADMIN
          /{id}
              GET – find by id – PERMİT ALL
              PUT – update food – ADMIN
             DELETE – delete food – ADMIN


Category Controller - /categories
    GET – all category – PERMİT ALL
    POST – save category – ADMIN
    /{id}
         GET – find by id – PERMİT ALL
         PUT – update category – ADMIN
         DELETE – delete category – ADMIN

        /products
             GET – find foods by category – PERMİT ALL










