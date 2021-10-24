# RAWG-Games-Gallery-Demo-App

**A Games gallery app using RAWG api**

 * [x] Gelen listedeki ilk 3 item android için ViewPager içine eklenecektir. 
 * [x] Kalanlar için RecyclerView oluşturuldu.
 * [x] Arama kutusuna ilk 3 harf girildikten sonra mevcut lokalde indirilmiş olan liste içinde ada göre arama yapılacaktır.
 * [x] İlk 3 harften sonra tek tek her harfte bir arama sorgusu yenilenecektir.
 * [x]  Arama esnasında ilk 3 harf girildikten sonra ViewPager yok olup, filtrelenmiş liste RecyclerView üzerinden gösterilecektir. 
 * [x]  Arama kutucuğu temizlendiğinde ekran ViewPager’lı eski haline geri dönecektir. 
 * [x]  Arama sonucunda bir sonuç bulunamaz ise ekrandaki ViewPager ve RecyclerView kaldırılıp, ekranın ortasında “Aradığınız oyun bulunamadı!” şeklinde bir TextView gösterilecek.
 * [x] BottomNavigationBar’da Ana Sayfa ve Favoriler şeklinde iki seçenek olacaktır. 
 * [x]  Favoriler sekmesine geçildiğinde kullanıcının daha önce beğendiği oyunların listelemesi olacaktır.
 * [x]  ViewPager ve RecyclerView’deki herhangi bir oyuna tıklandığında “Get game details” api kullanılarak detay sayfasına yönlendirme yapılacaktır.(Ana Sayfa ve Favori ekranlarının ikisinde de)


**Implements**

✔ Recyclerview - Viewpager2\
✔ Bumptech Glide (fotoğraf gösterimi için)\
✔ RoomDatabase (Database işlemleri için)\
✔ LiveData/ ViewModel Coroutines\
✔ Okhttp 4.9.0 (API kullanımı için)\
✔ OOP, SOLID (olabildiğince)


