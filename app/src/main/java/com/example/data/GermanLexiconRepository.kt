package com.example.data

import com.example.model.GermanArticle
import com.example.model.WordDetail

object GermanLexiconRepository {

    private val dictionary: Map<String, WordDetail> = listOf(
        // Geography & City Nouns
        WordDetail(
            word = "Stadt",
            root = "Stadt",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "مدينة",
            plural = "die Städte",
            phonetic = "[ʃtat]",
            grammarNote = "اسم مؤنث يأخذ أداة die وجمعه شاذ مع أوملاوت (die Städte)",
            exampleSentenceDe = "Berlin ist eine lebendige Stadt.",
            exampleSentenceAr = "برلين مدينة نابضة بالحياة."
        ),
        WordDetail(
            word = "Hauptstadt",
            root = "Hauptstadt",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "عاصمة",
            plural = "die Hauptstädte",
            phonetic = "[ˈhaʊ̯ptˌʃtat]",
            grammarNote = "اسم مركب: das Haupt + die Stadt (تتبع أداة الكلمة الأخيرة)",
            exampleSentenceDe = "Berlin ist die Hauptstadt von Deutschland.",
            exampleSentenceAr = "برلين هي عاصمة ألمانيا."
        ),
        WordDetail(
            word = "Deutschland",
            root = "Deutschland",
            article = GermanArticle.DAS,
            partOfSpeech = "Eigenname / Nomen (Neutrum)",
            arabicMeaning = "ألمانيا",
            plural = "—",
            phonetic = "[ˈdɔɪ̯t͡ʃlant]",
            grammarNote = "أسماء معظم الدول محايدة وتُستعمل عادة بدون أداة إلا مع صفة: das schöne Deutschland",
            exampleSentenceDe = "Deutschland liegt im Herzen Europas.",
            exampleSentenceAr = "تقع ألمانيا في قلب أوروبا."
        ),
        WordDetail(
            word = "Karte",
            root = "Karte",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "خريطة / بطاقة",
            plural = "die Karten",
            phonetic = "[ˈkaʁtə]",
            grammarNote = "معظم الكلمات المنتهية بـ -e مؤنثة تأخذ die",
            exampleSentenceDe = "Auf der Karte sehen wir alle Bundesländer.",
            exampleSentenceAr = "على الخريطة نرى جميع الولايات الفيدرالية."
        ),
        WordDetail(
            word = "Bundesland",
            root = "Bundesland",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "ولاية اتحادية",
            plural = "die Bundesländer",
            phonetic = "[ˈbʊndəsˌlant]",
            grammarNote = "مركب: der Bund (الاتحاد) + das Land (الأرض/الولاية) -> das",
            exampleSentenceDe = "Deutschland hat 16 Bundesländer.",
            exampleSentenceAr = "تتكون ألمانيا من 16 ولاية اتحادية."
        ),
        WordDetail(
            word = "Fluss",
            root = "Fluss",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "نهر",
            plural = "die Flüsse",
            phonetic = "[flʊs]",
            grammarNote = "اسم مذكر يأخذ der. الجمع: die Flüsse",
            exampleSentenceDe = "Der Rhein ist ein langer Fluss.",
            exampleSentenceAr = "نهر الراين نهر طويل."
        ),
        WordDetail(
            word = "Rhein",
            root = "Rhein",
            article = GermanArticle.DER,
            partOfSpeech = "Eigenname / Fluss (Maskulin)",
            arabicMeaning = "نهر الراين",
            plural = "—",
            phonetic = "[ʁaɪ̯n]",
            grammarNote = "معظم أنهار ألمانيا مذكرة (der Rhein, der Main, der Neckar)، باستثناء die Donau وdie Elbe وdie Mosel وdie Spree",
            exampleSentenceDe = "Köln liegt am Rhein.",
            exampleSentenceAr = "تقع كولونيا على نهر الراين."
        ),
        WordDetail(
            word = "Donau",
            root = "Donau",
            article = GermanArticle.DIE,
            partOfSpeech = "Eigenname / Fluss (Feminin)",
            arabicMeaning = "نهر الدانوب",
            plural = "—",
            phonetic = "[ˈdoːnaʊ̯]",
            grammarNote = "نهر مؤنث نادر في الألمانية: die Donau",
            exampleSentenceDe = "Die Donau fließt durch Bayern.",
            exampleSentenceAr = "يتدفق نهر الدانوب عبر بافاريا."
        ),
        WordDetail(
            word = "Elbe",
            root = "Elbe",
            article = GermanArticle.DIE,
            partOfSpeech = "Eigenname / Fluss (Feminin)",
            arabicMeaning = "نهر إلبه",
            plural = "—",
            phonetic = "[ˈɛlbə]",
            grammarNote = "نهر مؤنث: die Elbe",
            exampleSentenceDe = "Hamburg und Dresden liegen an der Elbe.",
            exampleSentenceAr = "تقع هامبورغ ودريسدن على نهر إلبه."
        ),
        WordDetail(
            word = "Spree",
            root = "Spree",
            article = GermanArticle.DIE,
            partOfSpeech = "Eigenname / Fluss (Feminin)",
            arabicMeaning = "نهر شبريه (نهر برلين)",
            plural = "—",
            phonetic = "[ʃpʁeː]",
            grammarNote = "نهر برلين الشهير: die Spree",
            exampleSentenceDe = "Die Spree fließt durch Berlin.",
            exampleSentenceAr = "يتدفق نهر شبريه عبر برلين."
        ),
        WordDetail(
            word = "Berg",
            root = "Berg",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "جبل",
            plural = "die Berge",
            phonetic = "[bɛʁk]",
            grammarNote = "مذكر يأخذ der. الجمع: die Berge",
            exampleSentenceDe = "Die Zugspitze ist der höchste Berg Deutschlands.",
            exampleSentenceAr = "قمة تسوغشبيتسه هي أعلى جبل في ألمانيا."
        ),
        WordDetail(
            word = "Alpen",
            root = "Alpen",
            article = GermanArticle.PLURAL,
            partOfSpeech = "Nomen (Plural)",
            arabicMeaning = "جبال الألب",
            plural = "die Alpen",
            phonetic = "[ˈalpn̩]",
            grammarNote = "يُستعمل بصيغة الجمع فقط: die Alpen",
            exampleSentenceDe = "Die Alpen liegen im Süden Bayerns.",
            exampleSentenceAr = "تقع جبال الألب في جنوب بافاريا."
        ),
        WordDetail(
            word = "Wald",
            root = "Wald",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "غابة",
            plural = "die Wälder",
            phonetic = "[valt]",
            grammarNote = "مذكر يأخذ der. الجمع: die Wälder",
            exampleSentenceDe = "Der Schwarzwald ist weltberühmt.",
            exampleSentenceAr = "الغابة السوداء مشهورة عالمياً."
        ),
        WordDetail(
            word = "Schwarzwald",
            root = "Schwarzwald",
            article = GermanArticle.DER,
            partOfSpeech = "Eigenname / Nomen (Maskulin)",
            arabicMeaning = "الغابة السوداء",
            plural = "—",
            phonetic = "[ˈʃvaʁt͡sˌvalt]",
            grammarNote = "مركب: schwarz (أسود) + der Wald (غابة) -> der",
            exampleSentenceDe = "Der Schwarzwald liegt in Baden-Württemberg.",
            exampleSentenceAr = "تقع الغابة السوداء في ولاية بادن-فورتمبيرغ."
        ),
        WordDetail(
            word = "See",
            root = "See",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "بحيرة (عندما تكون مذكر) / بحر (إذا كانت مؤنث die)",
            plural = "die Seen",
            phonetic = "[zeː]",
            grammarNote = "ملاحظة لغوية هامة: der See = بحيرة، بينما die See = بحر / يم!",
            exampleSentenceDe = "Der Bodensee ist der größte See Deutschlands.",
            exampleSentenceAr = "بحيرة كونستانس (بودنسيه) هي أكبر بحيرة في ألمانيا."
        ),
        WordDetail(
            word = "Meer",
            root = "Meer",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "بحر",
            plural = "die Meere",
            phonetic = "[meːɐ̯]",
            grammarNote = "محايد يأخذ das. الجمع: die Meere",
            exampleSentenceDe = "Die Nordsee und die Ostsee sind Randmeere.",
            exampleSentenceAr = "بحر الشمال وبحر البلطيق بحار مجاورة."
        ),
        WordDetail(
            word = "Nordsee",
            root = "Nordsee",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "بحر الشمال",
            plural = "—",
            phonetic = "[ˈnɔʁtˌzeː]",
            grammarNote = "بحر الشمال مؤنث: die See تعني البحر",
            exampleSentenceDe = "Bremen und Hamburg haben Zugang zur Nordsee.",
            exampleSentenceAr = "تبريمن وهامبورغ تتصلان ببحر الشمال."
        ),
        WordDetail(
            word = "Ostsee",
            root = "Ostsee",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "بحر البلطيق (الشرقي)",
            plural = "—",
            phonetic = "[ˈɔstˌzeː]",
            grammarNote = "مؤنث: die Ostsee",
            exampleSentenceDe = "Rostock liegt an der Ostsee.",
            exampleSentenceAr = "تقع روستوك على بحر البلطيق."
        ),

        // Landmarks & Architecture
        WordDetail(
            word = "Dom",
            root = "Dom",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "كاتدرائية كبرى",
            plural = "die Dome",
            phonetic = "[doːm]",
            grammarNote = "مذكر: der Dom. من أشهرها Kölner Dom",
            exampleSentenceDe = "Der Kölner Dom ist ein Meisterwerk der Gotik.",
            exampleSentenceAr = "كاتدرائية كولونيا تحفة من الطراز القوطي."
        ),
        WordDetail(
            word = "Kirche",
            root = "Kirche",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "كنيسة",
            plural = "die Kirchen",
            phonetic = "[ˈkɪʁçə]",
            grammarNote = "مؤنث منتهي بـ -e: die Kirche. الجمع: die Kirchen",
            exampleSentenceDe = "Die Frauenkirche in Dresden ist wiederaufgebaut.",
            exampleSentenceAr = "أعيد بناء كنيسة السيدة العذراء في دريسدن."
        ),
        WordDetail(
            word = "Schloss",
            root = "Schloss",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "قصر / قفل",
            plural = "die Schlösser",
            phonetic = "[ʃlɔs]",
            grammarNote = "محايد: das Schloss. الجمع مع أوملاوت: die Schlösser",
            exampleSentenceDe = "Schloss Neuschwanstein sieht wie ein Märchen aus.",
            exampleSentenceAr = "يبدو قصر نويشفانشتاين وكأنه من القصص الخيالية."
        ),
        WordDetail(
            word = "Burg",
            root = "Burg",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "قلعة / حصن تاريخي",
            plural = "die Burgen",
            phonetic = "[bʊʁk]",
            grammarNote = "مؤنث: die Burg (حصن دفاعي)، بخلاف das Schloss (قصر سكني)",
            exampleSentenceDe = "Die Wartburg in Eisenach ist historisch bedeutend.",
            exampleSentenceAr = "قلعة فارتبرغ في أيزناخ ذات أهمية تاريخية عظمى."
        ),
        WordDetail(
            word = "Tor",
            root = "Tor",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "بوابة / هدف (في الرياضة)",
            plural = "die Tore",
            phonetic = "[toːɐ̯]",
            grammarNote = "محايد: das Tor. أشهرها das Brandenburger Tor",
            exampleSentenceDe = "Das Brandenburger Tor ist das Symbol der Einheit.",
            exampleSentenceAr = "بوابة براندنبورغ هي رمز الوحدة الألمانية."
        ),
        WordDetail(
            word = "Rathaus",
            root = "Rathaus",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "دار البلدية / مبنى مجلس المدينة",
            plural = "die Rathäuser",
            phonetic = "[ˈʁaːtˌhaʊ̯s]",
            grammarNote = "مركب: der Rat (المجلس/الرأي) + das Haus (البيت) -> das",
            exampleSentenceDe = "Das Hamburger Rathaus hat über 600 Zimmer.",
            exampleSentenceAr = "تحتوي بلدية هامبورغ على أكثر من 600 غرفة."
        ),
        WordDetail(
            word = "Turm",
            root = "Turm",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "برج",
            plural = "die Türme",
            phonetic = "[tʊʁm]",
            grammarNote = "مذكر: der Turm. الجمع: die Türme",
            exampleSentenceDe = "Der Fernsehturm in Berlin ist 368 Meter hoch.",
            exampleSentenceAr = "يبلغ ارتفاع برج التلفزيون في برلين 368 متراً."
        ),
        WordDetail(
            word = "Museum",
            root = "Museum",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "متحف",
            plural = "die Museen",
            phonetic = "[muˈzeːʊm]",
            grammarNote = "الكلمات المنتهية بـ -um لاتينية ومحايدة (das)، وجمعها ينتهي بـ -en (die Museen)",
            exampleSentenceDe = "Die Museumsinsel in Berlin ist weltbekannt.",
            exampleSentenceAr = "جزيرة المتاحف في برلين مشهورة عالمياً."
        ),
        WordDetail(
            word = "Hafen",
            root = "Hafen",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "ميناء / مرفأ",
            plural = "die Häfen",
            phonetic = "[ˈhaːfn̩]",
            grammarNote = "مذكر: der Hafen. الجمع بأوملاوت: die Häfen",
            exampleSentenceDe = "Der Hamburger Hafen ist der größte Seehafen Deutschlands.",
            exampleSentenceAr = "ميناء هامبورغ هو أكبر ميناء بحري في ألمانيا."
        ),
        WordDetail(
            word = "Brücke",
            root = "Brücke",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "جسر",
            plural = "die Brücken",
            phonetic = "[ˈbʁʏkə]",
            grammarNote = "مؤنث منتهي بـ -e: die Brücke. الجمع: die Brücken",
            exampleSentenceDe = "Hamburg hat mehr Brücken als Venedig.",
            exampleSentenceAr = "تحتوي هامبورغ على جسور أكثر من البندقية."
        ),
        WordDetail(
            word = "Platz",
            root = "Platz",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "ساحة عامة / مكان / مقعد",
            plural = "die Plätze",
            phonetic = "[plat͡s]",
            grammarNote = "مذكر: der Platz. الجمع: die Plätze",
            exampleSentenceDe = "Der Marienplatz ist das Zentrum von München.",
            exampleSentenceAr = "ساحة مارين بلاتز هي مركز مدينة ميونخ."
        ),
        WordDetail(
            word = "Markt",
            root = "Markt",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "سوق",
            plural = "die Märkte",
            phonetic = "[maʁkt]",
            grammarNote = "مذكر: der Markt. الجمع: die Märkte",
            exampleSentenceDe = "Auf dem Weihnachtsmarkt gibt es Lebkuchen.",
            exampleSentenceAr = "في سوق عيد الميلاد توجد كعكات الزنجبيل."
        ),
        WordDetail(
            word = "Garten",
            root = "Garten",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "حديقة / بستان",
            plural = "die Gärten",
            phonetic = "[ˈɡaʁtn̩]",
            grammarNote = "مذكر: der Garten. الجمع: die Gärten",
            exampleSentenceDe = "Der Englische Garten in München ist riesig.",
            exampleSentenceAr = "الحديقة الإنجليزية في ميونخ شاسعة الحجم."
        ),
        WordDetail(
            word = "Park",
            root = "Park",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "منتزه / حديقة عامة",
            plural = "die Parks",
            phonetic = "[paʁk]",
            grammarNote = "مذكر: der Park. الجمع بصيغة الجمع الإنجليزي: die Parks",
            exampleSentenceDe = "Im Sommer sitzen die Menschen im Park.",
            exampleSentenceAr = "في الصيف يجلس الناس في المنتزه."
        ),
        WordDetail(
            word = "Universität",
            root = "Universität",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "جامعة",
            plural = "die Universitäten",
            phonetic = "[univɛʁziˈtɛːt]",
            grammarNote = "كل كلمة تنتهي بـ -tät مؤنثة دائماً تأخذ die (قاعدة نحوية ثابتة)",
            exampleSentenceDe = "Heidelberg hat die älteste Universität Deutschlands.",
            exampleSentenceAr = "تضم هايدلبرغ أقدم جامعة في ألمانيا (تأسست 1386م)."
        ),
        WordDetail(
            word = "Bibliothek",
            root = "Bibliothek",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "مكتبة عامة أو أكاديمية",
            plural = "die Bibliotheken",
            phonetic = "[bibli̯oˈteːk]",
            grammarNote = "الكلمات المنتهية بـ -thek مؤنثة دائماً (die)",
            exampleSentenceDe = "Die Herzogin-Anna-Amalia-Bibliothek ist in Weimar.",
            exampleSentenceAr = "مكتبة الدوقة آنا أماليا تقع في فايمار."
        ),
        WordDetail(
            word = "Oper",
            root = "Oper",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "دار الأوبرا",
            plural = "die Opern",
            phonetic = "[ˈoːpɐ]",
            grammarNote = "مؤنث: die Oper. مثل Semperoper in Dresden",
            exampleSentenceDe = "Die Semperoper in Dresden ist weltberühmt.",
            exampleSentenceAr = "دار الأوبرا سمبر أوبر في دريسدن شهيرة عالمياً."
        ),
        WordDetail(
            word = "Theater",
            root = "Theater",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "مسرح",
            plural = "die Theater",
            phonetic = "[teˈaːtɐ]",
            grammarNote = "محايد: das Theater. الجمع: die Theater",
            exampleSentenceDe = "Goethe und Schiller prägten das Theater in Weimar.",
            exampleSentenceAr = "صاغ غوته وشيلر المسرح في فايمار."
        ),

        // Culture, History, Society
        WordDetail(
            word = "Kultur",
            root = "Kultur",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "ثقافة / حضارة",
            plural = "die Kulturen",
            phonetic = "[kʊlˈtuːɐ̯]",
            grammarNote = "كل الكلمات المنتهية بـ -ur مؤنثة تأخذ die (die Natur, die Kultur)",
            exampleSentenceDe = "Deutschland hat eine reiche Kultur und Geschichte.",
            exampleSentenceAr = "تمتلك ألمانيا ثقافة وتاريخاً غنيين."
        ),
        WordDetail(
            word = "Geschichte",
            root = "Geschichte",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "تاريخ / قصة",
            plural = "die Geschichten",
            phonetic = "[ɡəˈʃɪçtə]",
            grammarNote = "مؤنث: die Geschichte",
            exampleSentenceDe = "Die Geschichte Nürnbergs reicht ins Mittelalter zurück.",
            exampleSentenceAr = "يمتد تاريخ نورمبرغ إلى العصور الوسطى."
        ),
        WordDetail(
            word = "Wirtschaft",
            root = "Wirtschaft",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "اقتصاد",
            plural = "die Wirtschaften",
            phonetic = "[ˈvɪʁtˌʃaft]",
            grammarNote = "اللاحقة -schaft تشير دوماً إلى اسم مؤنث (die)",
            exampleSentenceDe = "Frankfurt ist das Zentrum der Finanzwirtschaft.",
            exampleSentenceAr = "فرانكفورت هي مركز الاقتصاد والمال."
        ),
        WordDetail(
            word = "Bank",
            root = "Bank",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "مصرف / بنك (أو مقعد حديقة)",
            plural = "die Banken (مصارف) / die Bänke (مقاعد)",
            phonetic = "[baŋk]",
            grammarNote = "اسم مؤنث. انتبه لفرق الجمع: die Banken للمصارف، die Bänke للمقاعد",
            exampleSentenceDe = "Die Europäische Zentralbank hat ihren Sitz in Frankfurt.",
            exampleSentenceAr = "يقع مقر البنك المركزي الأوروبي في فرانكفورت."
        ),
        WordDetail(
            word = "Mauer",
            root = "Mauer",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "جدار / سور",
            plural = "die Mauern",
            phonetic = "[ˈmaʊ̯ɐ]",
            grammarNote = "مؤنث: die Mauer. أشهرها die Berliner Mauer",
            exampleSentenceDe = "Die Berliner Mauer fiel im Jahr 1989.",
            exampleSentenceAr = "سقط جدار برلين في عام 1989."
        ),
        WordDetail(
            word = "Einheit",
            root = "Einheit",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "وحدة / اتحاد",
            plural = "die Einheiten",
            phonetic = "[ˈaɪ̯nhaɪ̯t]",
            grammarNote = "اللاحقة -heit مؤنثة دائماً (die Freiheit, die Schönheit, die Einheit)",
            exampleSentenceDe = "Der 3. Oktober ist der Tag der Deutschen Einheit.",
            exampleSentenceAr = "الثالث من أكتوبر هو يوم الوحدة الألمانية."
        ),
        WordDetail(
            word = "Freiheit",
            root = "Freiheit",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "حرية",
            plural = "die Freiheiten",
            phonetic = "[ˈfʁaɪ̯haɪ̯t]",
            grammarNote = "تنتهي بـ -heit -> مؤنثة die",
            exampleSentenceDe = "Freiheit und Demokratie sind Grundwerte.",
            exampleSentenceAr = "الحرية والديمقراطية قيمتان أساسيتان."
        ),
        WordDetail(
            word = "Wissenschaft",
            root = "Wissenschaft",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "علم / علوم",
            plural = "die Wissenschaften",
            phonetic = "[ˈvɪsn̩ˌʃaft]",
            grammarNote = "اللاحقة -schaft مؤنثة دائماً: die Wissenschaft",
            exampleSentenceDe = "Göttingen ist berühmt für Wissenschaft und Nobelpreisträger.",
            exampleSentenceAr = "تشتهر غوتينغن بالعلوم وحائزي جوائز نوبل."
        ),
        WordDetail(
            word = "Erfindung",
            root = "Erfindung",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "اختراع",
            plural = "die Erfindungen",
            phonetic = "[ɛɐ̯ˈfɪndʊŋ]",
            grammarNote = "كل كلمة تنتهي بـ -ung مؤنثة تماماً تأخذ die (قاعدة ذهبية في الألمانية)",
            exampleSentenceDe = "Johannes Gutenberg erfand in Mainz den Buchdruck.",
            exampleSentenceAr = "اخترع يوهانس غوتنبرغ في ماينتس الطباعة بالحروف المتحركة."
        ),
        WordDetail(
            word = "Dichter",
            root = "Dichter",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "شاعر / أديب",
            plural = "die Dichter",
            phonetic = "[ˈdɪçtɐ]",
            grammarNote = "مذكر: der Dichter. المؤنث: die Dichterin",
            exampleSentenceDe = "Deutschland gilt als das Land der Dichter und Denker.",
            exampleSentenceAr = "تُعرف ألمانيا بأنها بلاد الشعراء والمفكرين."
        ),
        WordDetail(
            word = "Denker",
            root = "Denker",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "مفكر / فيلسوف",
            plural = "die Denker",
            phonetic = "[ˈdɛŋkɐ]",
            grammarNote = "مشتق من الفعل denken (يفكر): der Denker",
            exampleSentenceDe = "Große Denker wie Kant und Hegel lebten hier.",
            exampleSentenceAr = "عاش كبار المفكرين مثل كانط وهيغل هنا."
        ),
        WordDetail(
            word = "Mensch",
            root = "Mensch",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "إنسان / شخص",
            plural = "die Menschen",
            phonetic = "[mɛnʃ]",
            grammarNote = "اسم مذكر ضعيف (N-Deklination): des Menschen, dem Menschen",
            exampleSentenceDe = "Viele Menschen besuchen den Kölner Karneval.",
            exampleSentenceAr = "يزور العديد من الناس كرنفال كولونيا."
        ),
        WordDetail(
            word = "Leben",
            root = "Leben",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "حياة / معيشة",
            plural = "die Leben",
            phonetic = "[ˈleːbn̩]",
            grammarNote = "اسم مشتق من المصدر leben (يعيش)، والمصادر المعرّفة دائماً محايدة (das)",
            exampleSentenceDe = "Das kulturelle Leben in Berlin ist faszinierend.",
            exampleSentenceAr = "الحياة الثقافية في برلين ساحرة."
        ),
        WordDetail(
            word = "Wort",
            root = "Wort",
            article = GermanArticle.DAS,
            partOfSpeech = "Nomen (Neutrum)",
            arabicMeaning = "كلمة / لفظ",
            plural = "die Wörter (مفردات منفصلة) / die Worte (كلام معبر)",
            phonetic = "[vɔʁt]",
            grammarNote = "محايد: das Wort. الجمع: die Wörter (قائمة كلمات)، die Worte (أقوال)",
            exampleSentenceDe = "Jedes deutsche Wort hat einen genauen Artikel.",
            exampleSentenceAr = "لكل كلمة ألمانية أداة تعريف دقيقة."
        ),
        WordDetail(
            word = "Klang",
            root = "Klang",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "صوت / رنين / نغمة موسيقية",
            plural = "die Klänge",
            phonetic = "[klaŋ]",
            grammarNote = "مذكر: der Klang. الجمع بأوملاوت: die Klänge",
            exampleSentenceDe = "Der Klang der deutschen Sprache ist klar und ausdrucksstark.",
            exampleSentenceAr = "صوت ونبرة اللغة الألمانية واضحة ومعبرة."
        ),
        WordDetail(
            word = "Sprache",
            root = "Sprache",
            article = GermanArticle.DIE,
            partOfSpeech = "Nomen (Feminin)",
            arabicMeaning = "لغة / لسان",
            plural = "die Sprachen",
            phonetic = "[ˈʃpʁaːxə]",
            grammarNote = "مؤنث: die Sprache. مشتقة من sprechen (يتكلم)",
            exampleSentenceDe = "Deutsch ist eine faszinierende germanische Sprache.",
            exampleSentenceAr = "الألمانية لغة جرمانية ممتعة ورائعة."
        ),
        WordDetail(
            word = "Dialekt",
            root = "Dialekt",
            article = GermanArticle.DER,
            partOfSpeech = "Nomen (Maskulin)",
            arabicMeaning = "لهجة محلية",
            plural = "die Dialekte",
            phonetic = "[diaˈlɛkt]",
            grammarNote = "مذكر: der Dialekt. مثل البافارية والسكسونية والبرلينية",
            exampleSentenceDe = "In Bayern spricht man einen besonderen Dialekt.",
            exampleSentenceAr = "يتحدث الناس في بافاريا لهجة خاصة ومميزة."
        ),

        // Key Verbs
        WordDetail(
            word = "liegen",
            root = "liegen",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb (غير متعدٍ)",
            arabicMeaning = "يقع / يوجد في مكان / يتمدد",
            plural = null,
            phonetic = "[ˈliːɡn̩]",
            grammarNote = "فعل شاذ: lag, gelegen. يُعبر عن الموقع الجغرافي للمدن",
            exampleSentenceDe = "München liegt im Süden von Deutschland.",
            exampleSentenceAr = "تقع ميونخ في جنوب ألمانيا."
        ),
        WordDetail(
            word = "fließen",
            root = "fließen",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb (غير متعدٍ)",
            arabicMeaning = "يتدفق / يجري (للماء والأنهار)",
            plural = null,
            phonetic = "[ˈfliːsn̩]",
            grammarNote = "فعل شاذ: floss, geflossen",
            exampleSentenceDe = "Der Fluss fließt durch die Altstadt.",
            exampleSentenceAr = "يجري النهر عبر البلدة القديمة."
        ),
        WordDetail(
            word = "besuchen",
            root = "besuchen",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb (متعدٍ)",
            arabicMeaning = "يزور",
            plural = null,
            phonetic = "[bəˈzuːxn̩]",
            grammarNote = "فعل نظامي لا يقبل الانفصال (be- لا تنفصل)",
            exampleSentenceDe = "Millionen Touristen besuchen Köln jedes Jahr.",
            exampleSentenceAr = "يزور ملايين السياح كولونيا كل عام."
        ),
        WordDetail(
            word = "entdecken",
            root = "entdecken",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb (متعدٍ)",
            arabicMeaning = "يكتشف / يستكشف",
            plural = null,
            phonetic = "[ɛntˈdɛkn̩]",
            grammarNote = "البادئة ent- لا تنفصل",
            exampleSentenceDe = "Auf der interaktiven Karte entdecken wir neue Städte.",
            exampleSentenceAr = "على الخريطة التفاعلية نكتشف مدناً جديدة."
        ),
        WordDetail(
            word = "leben",
            root = "leben",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb",
            arabicMeaning = "يعيش / يحيا",
            plural = null,
            phonetic = "[ˈleːbn̩]",
            grammarNote = "فعل نظامي: lebte, gelebt",
            exampleSentenceDe = "Über 84 Millionen Menschen leben in Deutschland.",
            exampleSentenceAr = "يعيش أكثر من 84 مليون شخص في ألمانيا."
        ),
        WordDetail(
            word = "feiern",
            root = "feiern",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb",
            arabicMeaning = "يحتفل",
            plural = null,
            phonetic = "[ˈfaɪ̯ɐn]",
            grammarNote = "فعل نظامي",
            exampleSentenceDe = "Die Menschen feiern das Oktoberfest.",
            exampleSentenceAr = "يحتفل الناس بمهرجان أكتوبر."
        ),
        WordDetail(
            word = "gründen",
            root = "gründen",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb",
            arabicMeaning = "يؤسس / ينشئ",
            plural = null,
            phonetic = "[ˈɡʁʏndn̩]",
            grammarNote = "المبني للمجهول: wurde gegründet (تأسست)",
            exampleSentenceDe = "Trier wurde von den Römern gegründet.",
            exampleSentenceAr = "تأسست ترير على يد الرومان."
        ),
        WordDetail(
            word = "hören",
            root = "hören",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb",
            arabicMeaning = "يسمع / يستمع",
            plural = null,
            phonetic = "[ˈhøːʁn̩]",
            grammarNote = "فعل نظامي",
            exampleSentenceDe = "Hier hören wir die korrekte Aussprache.",
            exampleSentenceAr = "هنا نستمع إلى النطق الصحيح."
        ),
        WordDetail(
            word = "sprechen",
            root = "sprechen",
            article = GermanArticle.NONE,
            partOfSpeech = "Verb",
            arabicMeaning = "يتحدث / ينطق",
            plural = null,
            phonetic = "[ˈʃpʁɛçn̩]",
            grammarNote = "فعل قوي مع تغير الحرف الصوتي: du sprichst, er spricht; sprach, gesprochen",
            exampleSentenceDe = "In Deutschland spricht man Hochdeutsch.",
            exampleSentenceAr = "في ألمانيا يتحدث الناس الألمانية الفصحى (Hochdeutsch)."
        ),

        // Key Adjectives
        WordDetail(
            word = "berühmt",
            root = "berühmt",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "مشهور / ذائع الصيت",
            plural = null,
            phonetic = "[bəˈʁyːmt]",
            grammarNote = "صفة. الاسم المشتق: die Berühmtheit",
            exampleSentenceDe = "Der Kölner Dom ist weltberühmt.",
            exampleSentenceAr = "كاتدرائية كولونيا مشهورة عالمياً."
        ),
        WordDetail(
            word = "bekannt",
            root = "bekannt",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "معروف / مألوف",
            plural = null,
            phonetic = "[bəˈkant]",
            grammarNote = "صفة مشتقة من kennen (يعرف)",
            exampleSentenceDe = "Nürnberg ist bekannt für seine Lebkuchen.",
            exampleSentenceAr = "نورمبرغ معروفة بكعك الزنجبيل (Lebkuchen)."
        ),
        WordDetail(
            word = "historisch",
            root = "historisch",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "تاريخي / عريق",
            plural = null,
            phonetic = "[hɪsˈtoːʁɪʃ]",
            grammarNote = "اللاحقة -isch تحول الأسماء إلى صفات",
            exampleSentenceDe = "Rothenburg hat ein historisches Stadtzentrum.",
            exampleSentenceAr = "تمتلك روتنبورغ مركز مدينة تاريخياً."
        ),
        WordDetail(
            word = "modern",
            root = "modern",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "حديث / معاصر",
            plural = null,
            phonetic = "[moˈdɛʁn]",
            grammarNote = "صفة",
            exampleSentenceDe = "Frankfurt hat eine moderne Skyline.",
            exampleSentenceAr = "تمتلك فرانكفورت أفقاً معمارياً حديثاً وناطحات سحاب."
        ),
        WordDetail(
            word = "groß",
            root = "groß",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "كبير / عظيم",
            plural = null,
            phonetic = "[ɡʁoːs]",
            grammarNote = "المقارنة: größer, am größten",
            exampleSentenceDe = "Berlin ist die größte Stadt Deutschlands.",
            exampleSentenceAr = "برلين هي أكبر مدينة في ألمانيا."
        ),
        WordDetail(
            word = "alt",
            root = "alt",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "قديم / عتيق",
            plural = null,
            phonetic = "[alt]",
            grammarNote = "المقارنة بأوملاوت: älter, am ältesten",
            exampleSentenceDe = "Trier ist eine der ältesten Städte.",
            exampleSentenceAr = "تعد ترير من أقدم المدن الألمانية."
        ),
        WordDetail(
            word = "schön",
            root = "schön",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "جميل / رائع",
            plural = null,
            phonetic = "[ʃøːn]",
            grammarNote = "صفة. الاسم المشتق: die Schönheit",
            exampleSentenceDe = "Heidelberg hat eine wunderschöne Altstadt.",
            exampleSentenceAr = "تمتلك هايدلبرغ بلدة قديمة فائقة الجمال."
        ),
        WordDetail(
            word = "wichtig",
            root = "wichtig",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "مهم / ذو شأن",
            plural = null,
            phonetic = "[ˈvɪçtɪç]",
            grammarNote = "اللاحقة -ig شائعة في الصفات وتُنطق مثل ich في نهاية الكلمة",
            exampleSentenceDe = "Frankfurt ist ein wichtiger Verkehrsknotenpunkt.",
            exampleSentenceAr = "فرانكفورت ملتقى طرق مواصلات هام."
        ),
        WordDetail(
            word = "lebendig",
            root = "lebendig",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "حي / نابض بالحياة",
            plural = null,
            phonetic = "[leˈbɛndɪç]",
            grammarNote = "صفة تعبر عن النشاط والحيوية",
            exampleSentenceDe = "Leipzig ist eine junge, lebendige Kunststadt.",
            exampleSentenceAr = "لايبزيغ مدينة فنون شابة ونابضة بالحيوية."
        ),
        WordDetail(
            word = "reich",
            root = "reich",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "غني / ثري",
            plural = null,
            phonetic = "[ʁaɪ̯ç]",
            grammarNote = "صفة",
            exampleSentenceDe = "Deutschland ist reich an Traditionen.",
            exampleSentenceAr = "ألمانيا غنية بالتقاليد والتراث."
        ),
        WordDetail(
            word = "grün",
            root = "grün",
            article = GermanArticle.NONE,
            partOfSpeech = "Adjektiv",
            arabicMeaning = "أخضر / صديق للبيئة",
            plural = null,
            phonetic = "[ɡʁyːn]",
            grammarNote = "صفة ولون",
            exampleSentenceDe = "Freiburg ist die grünste Stadt Deutschlands.",
            exampleSentenceAr = "فرايبورغ هي أكثر مدن ألمانيا خضرة وصداقة للبيئة."
        )
    ).associateBy { it.word.lowercase() }

    /**
     * Look up any German word. Handles casing, punctuation, declension endings, and returns
     * an intelligent linguistic breakdown with the correct article and Arabic translation.
     */
    fun lookupWord(rawWord: String): WordDetail {
        val clean = rawWord.trim().replace(Regex("[^\\p{L}\\-äöüßÄÖÜ]"), "")
        if (clean.isBlank()) {
            return WordDetail(
                word = rawWord,
                partOfSpeech = "Zeichen",
                arabicMeaning = "رمز / علامة",
                article = GermanArticle.NONE
            )
        }

        val lower = clean.lowercase()

        // 1. Direct dictionary match
        dictionary[lower]?.let { return it.copy(word = clean, isFavorite = isFavorite(lower)) }

        // 2. Try removing common inflection and superlative endings
        val candidateRoots = listOf(
            lower.removeSuffix("sten"),
            lower.removeSuffix("ste"),
            lower.removeSuffix("ster"),
            lower.removeSuffix("stes"),
            lower.removeSuffix("eren"),
            lower.removeSuffix("ere"),
            lower.removeSuffix("er"),
            lower.removeSuffix("en"),
            lower.removeSuffix("em"),
            lower.removeSuffix("es"),
            lower.removeSuffix("te"),
            lower.removeSuffix("test"),
            lower.removeSuffix("tet"),
            lower.removeSuffix("e"),
            lower.removeSuffix("s"),
            lower.removeSuffix("n")
        ).filter { it.length >= 3 }

        for (candidate in candidateRoots) {
            dictionary[candidate]?.let {
                return it.copy(
                    word = clean,
                    root = it.word,
                    isFavorite = isFavorite(candidate),
                    grammarNote = "صيغة مصرفية / معربة من: ${it.displayWithArticle} (أصل المفردة)"
                )
            }
        }

        // 3. Participle II stemmer: ge- + root + -t or -en (e.g. gebaut -> bauen, gegründet -> gründen)
        if (lower.startsWith("ge") && lower.length > 5) {
            val withoutGe = lower.removePrefix("ge")
            val participleRoots = listOf(
                withoutGe.removeSuffix("t") + "en",
                withoutGe.removeSuffix("t") + "n",
                withoutGe.removeSuffix("en") + "en",
                withoutGe.removeSuffix("et") + "en"
            )
            for (pRoot in participleRoots) {
                dictionary[pRoot]?.let {
                    return it.copy(
                        word = clean,
                        root = it.word,
                        isFavorite = isFavorite(pRoot),
                        grammarNote = "اسم مفعول / تصريف ماضي (Partizip II) من الفعل: ${it.word}"
                    )
                }
            }
        }

        // 4. German Compound Noun Rule (Das Grundwort bestimmt Genus und Artikel):
        // If a word is a compound noun, the last word determines its article, gender and plural!
        if (clean.firstOrNull()?.isUpperCase() == true && clean.length > 6) {
            // Find if the word ends with any known dictionary noun
            for ((key, baseDetail) in dictionary) {
                if (key.length >= 4 && lower.endsWith(key) && lower != key && baseDetail.article != GermanArticle.NONE) {
                    val prefixPart = clean.substring(0, clean.length - key.length)
                    return WordDetail(
                        word = clean,
                        root = baseDetail.word,
                        article = baseDetail.article,
                        partOfSpeech = "Kompositum (${baseDetail.partOfSpeech})",
                        arabicMeaning = "${generateArabicGlossary(prefixPart, "Präfix", GermanArticle.NONE)} + ${baseDetail.arabicMeaning}",
                        plural = if (baseDetail.plural != null) "die $prefixPart${baseDetail.plural.removePrefix("die ")}" else null,
                        phonetic = generateApproxIpa(clean),
                        grammarNote = "اسم مركب ألماني (Kompositum): الكلمة الأخيرة «${baseDetail.displayWithArticle}» هي التي تحدد أداة التعريف (${baseDetail.article.articleStr}) والجمع.",
                        exampleSentenceDe = "«$clean» ist ein zusammengesetztes Nomen.",
                        exampleSentenceAr = "«$clean» اسم ألماني مركب يتكون من $prefixPart و ${baseDetail.word}.",
                        isFavorite = isFavorite(lower)
                    )
                }
            }
        }

        // 5. Morphological & suffix linguistic rule engine for unknown words
        val article = inferArticleBySuffix(clean)
        val pos = inferPartOfSpeech(clean, article)
        val phonetic = generateApproxIpa(clean)
        val note = generateGrammarNote(clean, article, pos)

        return WordDetail(
            word = clean,
            root = clean,
            article = article,
            partOfSpeech = pos,
            arabicMeaning = generateArabicGlossary(clean, pos, article),
            plural = if (article != GermanArticle.NONE) "die ${clean}en" else null,
            phonetic = phonetic,
            grammarNote = note,
            exampleSentenceDe = "$clean ist ein interessantes deutsches Wort.",
            exampleSentenceAr = "«$clean» كلمة ألمانية هامة في السياق اللغوي.",
            isFavorite = isFavorite(lower)
        )
    }

    private fun inferArticleBySuffix(word: String): GermanArticle {
        val lower = word.lowercase()
        return when {
            // Feminine endings
            lower.endsWith("ung") || lower.endsWith("heit") || lower.endsWith("keit") ||
            lower.endsWith("schaft") || lower.endsWith("tät") || lower.endsWith("ion") ||
            lower.endsWith("ik") || lower.endsWith("ei") || lower.endsWith("ur") -> GermanArticle.DIE

            // Neuter endings
            lower.endsWith("chen") || lower.endsWith("lein") || lower.endsWith("ment") ||
            lower.endsWith("um") || lower.endsWith("nis") || lower.endsWith("tum") -> GermanArticle.DAS

            // Masculine endings
            lower.endsWith("er") || lower.endsWith("or") || lower.endsWith("ismus") ||
            lower.endsWith("ist") || lower.endsWith("ant") || lower.endsWith("ling") -> GermanArticle.DER

            // Plural marker
            word[0].isUpperCase() && (lower.endsWith("en") || lower.endsWith("er")) -> GermanArticle.PLURAL

            // Default: if capitalized, probable noun, default to neutral/contextual
            word[0].isUpperCase() -> GermanArticle.DAS
            else -> GermanArticle.NONE
        }
    }

    private fun inferPartOfSpeech(word: String, article: GermanArticle): String {
        return when {
            article != GermanArticle.NONE -> {
                when (article) {
                    GermanArticle.DER -> "Nomen (Maskulin - اسم مذكر)"
                    GermanArticle.DIE -> "Nomen (Feminin - اسم مؤنث)"
                    GermanArticle.DAS -> "Nomen (Neutrum - اسم محايد)"
                    GermanArticle.PLURAL -> "Nomen (Plural - اسم جمع)"
                    else -> "Nomen (اسم)"
                }
            }
            word.endsWith("en") || word.endsWith("t") || word.endsWith("te") -> "Verb (فعل)"
            word.endsWith("ig") || word.endsWith("lich") || word.endsWith("isch") || word.endsWith("bar") -> "Adjektiv (صفة)"
            word.length <= 4 -> "Partikel / Präposition (حرف أو أداة)"
            else -> "Wort (كلمة ألمانية)"
        }
    }

    private fun generateApproxIpa(word: String): String {
        var ipa = word.lowercase()
        ipa = ipa.replace("sch", "ʃ")
        ipa = ipa.replace("ch", "ç")
        ipa = ipa.replace("ei", "aɪ̯")
        ipa = ipa.replace("eu", "ɔɪ̯")
        ipa = ipa.replace("äu", "ɔɪ̯")
        ipa = ipa.replace("ie", "iː")
        ipa = ipa.replace("ä", "ɛː")
        ipa = ipa.replace("ö", "øː")
        ipa = ipa.replace("ü", "yː")
        ipa = ipa.replace("ß", "s")
        ipa = ipa.replace("st", "ʃt")
        ipa = ipa.replace("sp", "ʃp")
        return "[$ipa]"
    }

    private fun generateGrammarNote(word: String, article: GermanArticle, pos: String): String {
        return when {
            article == GermanArticle.DIE && word.endsWith("ung") -> "اللاحقة -ung تصوغ أسماء مؤنثة تأخذ دائماً أداة die"
            article == GermanArticle.DIE && word.endsWith("heit") -> "اللاحقة -heit تصوغ أسماء مؤنثة تأخذ دائماً أداة die"
            article == GermanArticle.DIE && word.endsWith("keit") -> "اللاحقة -keit تصوغ أسماء مؤنثة تأخذ دائماً أداة die"
            article == GermanArticle.DIE && word.endsWith("schaft") -> "اللاحقة -schaft تعني حالة أو هيئة وتأخذ دائماً أداة die"
            article == GermanArticle.DIE && word.endsWith("tät") -> "اللاحقة -tät لاتينية الأصل وتأخذ دائماً أداة die"
            article == GermanArticle.DAS && (word.endsWith("chen") || word.endsWith("lein")) -> "صيغ التصغير -chen و-lein محايدة دائماً تأخذ das"
            article == GermanArticle.DER && word.endsWith("er") -> "اللاحقة -er تدل غالباً على الفاعل المذكر وتأخذ der"
            word[0].isUpperCase() -> "في الألمانية تُكتب جميع الأسماء بحرف كبير (Großschreibung) ويجب حفظها مع أداتها"
            else -> "كلمة ألمانية في سياق النص"
        }
    }

    private fun generateArabicGlossary(word: String, pos: String, article: GermanArticle): String {
        val lower = word.lowercase()
        return when (lower) {
            "und" -> "وَ (حرف عطف)"
            "in" -> "في"
            "im" -> "في الـ (in + dem)"
            "am" -> "على / عند / بجوار (an + dem)"
            "an" -> "على / عند (بمحاذاة)"
            "auf" -> "على (فوق السطح)"
            "aus" -> "من (بلد أو أصل أو مادة)"
            "bei" -> "عند / لدى / بالقرب من"
            "beim" -> "عند الـ (bei + dem)"
            "mit" -> "مع / بواسطة"
            "nach" -> "إلى (المدن والبلدان) / بعد"
            "von" -> "من / الخاص بـ (ملكية)"
            "vom" -> "من الـ (von + dem)"
            "zu" -> "إلى / نحو / مغلق"
            "zum" -> "إلى الـ (zu + dem)"
            "zur" -> "إلى الـ (zu + der)"
            "für" -> "لأجل / لـ"
            "über" -> "فوق / عن / أكثر من"
            "unter" -> "تحت / بين"
            "durch" -> "عبر / من خلال"
            "um" -> "حول / في تمام الساعة"
            "gegen" -> "ضد / حوالي"
            "zwischen" -> "بين"
            "hinter" -> "خلف"
            "vor" -> "أمام / قبل"
            "neben" -> "بجانب"
            "ab" -> "ابتداءً من"
            "bis" -> "حتى / إلى غاية"
            "seit" -> "منذ"
            "der" -> "أداة تعريف للمذكر (Nominativ)"
            "die" -> "أداة تعريف للمؤنث والجمع"
            "das" -> "أداة تعريف للمحايد"
            "den" -> "أداة تعريف للمذكر (Akkusativ) أو الجمع (Dativ)"
            "dem" -> "أداة تعريف للمذكر والمحايد (Dativ)"
            "des" -> "أداة تعريف للمذكر والمحايد (Genitiv)"
            "ein" -> "أداة نكرة للمذكر والمحايد"
            "eine" -> "أداة نكرة للمؤنث"
            "einen" -> "أداة نكرة للمذكر (Akkusativ)"
            "einem" -> "أداة نكرة للمذكر والمحايد (Dativ)"
            "einer" -> "أداة نكرة للمؤنث (Dativ / Genitiv)"
            "eines" -> "أداة نكرة للمذكر والمحايد (Genitiv)"
            "kein", "keine", "keinen", "keinem" -> "أداة نفي للأسماء النكرة"
            "ist" -> "يكون (فعل الكينونة sein)"
            "sind" -> "يكونون / هم يكونون"
            "war", "waren" -> "كان / كانوا (ماضي sein)"
            "wird", "werden" -> "يصبح / سيصبح / صيغة مبني للمجهول"
            "wurde", "wurden" -> "أُنشئ / أصبح / وُجد (ماضي مبني للمجهول)"
            "hat", "haben" -> "يمتلك / يملكون (أو فعل مساعد للماضي)"
            "hatte", "hatten" -> "امتلك / امتلكوا (ماضي haben)"
            "liegt", "liegen" -> "يقع / تقع (جغرافياً)"
            "gibt", "es gibt" -> "يوجد / هناك"
            "zählt", "zählen" -> "يُعد / يعتبر ضمن"
            "gilt" -> "يُعتبر / يسري مفعوله"
            "bietet", "bieten" -> "يُقدّم / يمنح / يتيح"
            "steht", "stehen" -> "يقف / يرتفع / يتواجد"
            "fließt", "fließen" -> "يتدفق / يجري (للنهر)"
            "gehört", "gehören" -> "ينتمي إلى / يتبع لـ"
            "befindet", "befinden" -> "يتواجد / يقع"
            "stammt", "stammen" -> "يعود أصله إلى"
            "als" -> "كـ (صفة أو دور) / عندما / من (في المقارنة)"
            "wie" -> "مثل / كـ / كيف"
            "aber" -> "لكن / إلا أن"
            "oder" -> "أو"
            "denn" -> "لأن / إذن"
            "weil" -> "لأن (تضع الفعل في نهاية الجملة)"
            "dass" -> "أنّ"
            "wenn" -> "إذا / لو / عندما"
            "obwohl" -> "على الرغم من"
            "damit" -> "لكي / حتى"
            "sehr" -> "جداً"
            "auch" -> "أيضاً"
            "nicht" -> "لا / ليس (نفي)"
            "nur" -> "فقط"
            "noch" -> "لا يزال / بعد"
            "schon" -> "بالفعل / مسبقاً"
            "bereits" -> "بالفعل"
            "immer" -> "دائماً"
            "oft" -> "غالباً"
            "hier" -> "هنا"
            "dort" -> "هناك"
            "da" -> "هناك / حيثما"
            "heute" -> "اليوم / في الوقت الحاضر"
            "damals" -> "آنذاك / في ذلك الوقت"
            "früher" -> "سابقاً / قديماً"
            "jetzt" -> "الآن"
            "nie" -> "أبداً / مطلقاً"
            "alle" -> "كل / جميع"
            "viele", "vielen" -> "كثير من"
            "groß", "große", "großer", "großes", "großen" -> "كبير"
            "größte", "größten", "größter" -> "الأكبر / الأعظم"
            "klein", "kleine", "kleiner" -> "صغير"
            "alt", "alte", "alter", "älteste", "ältesten" -> "قديم / الأقدم"
            "neu", "neue", "neuer", "neueste" -> "جديد"
            "wichtig", "wichtige", "wichtiger", "wichtigste", "wichtigsten" -> "مهم / الأهم"
            "berühmt", "berühmte", "berühmter", "berühmteste", "berühmtesten" -> "مشهور / الأشهر"
            "schön", "schöne", "schöner", "schönste" -> "جميل / الأجمل"
            "weit", "weitere", "weiteren" -> "بعيد / إضافي"
            "weltweit" -> "عالمياً / حول العالم"
            "international" -> "دولي / عالمي"
            "kulturell", "kulturelle" -> "ثقافي"
            "historisch", "historische", "historischen" -> "تاريخي"
            "wirtschaftlich" -> "اقتصادي"
            "bekannt", "bekannte", "bekannter" -> "معروف"
            "gesamt", "gesamte" -> "كامل / شامل"
            "deutsch", "deutsche", "deutschen", "deutscher" -> "ألماني"
            "erste", "ersten", "erster" -> "الأول"
            "zweite", "zweiten" -> "الثاني"
            "dritte", "dritten" -> "الثالث"
            "jahrhundert", "jahrhunderts" -> "قرن من الزمان"
            "geschichte" -> "تاريخ / قصة"
            "kultur" -> "ثقافة"
            "natur" -> "طبيعة"
            "leben" -> "حياة / يعيش"
            "menschen" -> "بشر / ناس"
            "einwohner" -> "سكان / قاطنون"
            "kilometer" -> "كيلومتر"
            "meter" -> "متر"
            "hauptstadt" -> "عاصمة (die Stadt)"
            "hafenstadt" -> "مدينة ساحلية / ميناء (die Stadt)"
            "altstadt" -> "البلدة القديمة التاريخية (die Stadt)"
            "innenstadt" -> "وسط المدينة (die Stadt)"
            "großstadt" -> "مدينة كبرى (die Stadt)"
            "kleinstadt" -> "بلدة صغيرة (die Stadt)"
            else -> if (article != GermanArticle.NONE) "مفردة ${article.genderAr}" else "كلمة ألمانية"
        }
    }

    private val favoriteWords = mutableSetOf<String>()

    fun toggleFavorite(word: String) {
        val lower = word.lowercase().trim()
        if (favoriteWords.contains(lower)) {
            favoriteWords.remove(lower)
        } else {
            favoriteWords.add(lower)
        }
    }

    fun isFavorite(word: String): Boolean = favoriteWords.contains(word.lowercase().trim())

    fun getAllWords(): List<WordDetail> = dictionary.values.map {
        it.copy(isFavorite = favoriteWords.contains(it.word.lowercase()))
    }
}
