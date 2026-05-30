// Built-in Exercise Database with Muscle Activation Ratios

export const INITIAL_EXERCISES = [
    // --- GÖĞÜS (CHEST) ---
    {
        id: "bench_press",
        name: "Bench Press",
        category: "göğüs",
        type: "compound",
        description: "Yatay sehpada barbell göğüs presi.",
        muscles: { chest: 0.8, shoulders: 0.15, triceps: 0.05 }
    },
    {
        id: "incline_bench_press",
        name: "Incline Bench Press",
        category: "göğüs",
        type: "compound",
        description: "Eğimli sehpada barbell göğüs presi (üst göğüs).",
        muscles: { chest: 0.7, shoulders: 0.2, triceps: 0.1 }
    },
    {
        id: "decline_bench_press",
        name: "Decline Bench Press",
        category: "göğüs",
        type: "compound",
        description: "Ters eğimli sehpada barbell göğüs presi (alt göğüs).",
        muscles: { chest: 0.85, shoulders: 0.05, triceps: 0.1 }
    },
    {
        id: "dumbbell_fly",
        name: "Dumbbell Fly",
        category: "göğüs",
        type: "isolation",
        description: "Dumbbell ile göğüs açış.",
        muscles: { chest: 0.95, shoulders: 0.05 }
    },
    {
        id: "chest_press_machine",
        name: "Chest Press (Makine)",
        category: "göğüs",
        type: "compound",
        description: "Makinede göğüs presi.",
        muscles: { chest: 0.8, shoulders: 0.1, triceps: 0.1 }
    },
    {
        id: "cable_crossover",
        name: "Cable Crossover",
        category: "göğüs",
        type: "isolation",
        description: "Kabloda göğüs sıkıştırma.",
        muscles: { chest: 0.95, shoulders: 0.05 }
    },
    {
        id: "dips_chest",
        name: "Dips (Göğüs)",
        category: "göğüs",
        type: "compound",
        description: "Öne eğilerek göğüs odaklı dips.",
        muscles: { chest: 0.65, triceps: 0.25, shoulders: 0.1 }
    },

    // --- SIRT (BACK) ---
    {
        id: "deadlift",
        name: "Deadlift",
        category: "sırt",
        type: "compound",
        description: "Yerden barbell ile doğrularak yapılan tüm vücut egzersizi.",
        muscles: { back: 0.4, legs: 0.4, core: 0.2 }
    },
    {
        id: "pullups",
        name: "Barfiks (Pull-up)",
        category: "sırt",
        type: "compound",
        description: "Geniş tutuş vücut ağırlığı ile barfiks.",
        muscles: { back: 0.75, biceps: 0.2, shoulders: 0.05 }
    },
    {
        id: "lat_pulldown",
        name: "Lat Pulldown",
        category: "sırt",
        type: "compound",
        description: "Makinede geniş tutuş sırta çekiş.",
        muscles: { back: 0.8, biceps: 0.15, shoulders: 0.05 }
    },
    {
        id: "barbell_row",
        name: "Barbell Row",
        category: "sırt",
        type: "compound",
        description: "Barbell ile eğilerek sırta çekiş.",
        muscles: { back: 0.7, biceps: 0.15, shoulders: 0.05, core: 0.1 }
    },
    {
        id: "dumbbell_row",
        name: "Dumbbell Row",
        category: "sırt",
        type: "compound",
        description: "Tek kol dumbbell sırta çekiş.",
        muscles: { back: 0.8, biceps: 0.2 }
    },
    {
        id: "cable_row",
        name: "Seated Cable Row",
        category: "sırt",
        type: "compound",
        description: "Kabloda oturarak sırta çekiş.",
        muscles: { back: 0.8, biceps: 0.2 }
    },
    {
        id: "hyper_extension",
        name: "Hyperextension",
        category: "sırt",
        type: "isolation",
        description: "Bel uzatma hareketi.",
        muscles: { back: 0.7, legs: 0.2, core: 0.1 }
    },

    // --- OMUZ (SHOULDERS) ---
    {
        id: "overhead_press",
        name: "Overhead Press (OHP)",
        category: "omuz",
        type: "compound",
        description: "Ayakta barbell omuz presi.",
        muscles: { shoulders: 0.75, triceps: 0.15, core: 0.1 }
    },
    {
        id: "dumbbell_shoulder_press",
        name: "Dumbbell Shoulder Press",
        category: "omuz",
        type: "compound",
        description: "Oturarak dumbbell omuz presi.",
        muscles: { shoulders: 0.8, triceps: 0.2 }
    },
    {
        id: "lateral_raise",
        name: "Lateral Raise",
        category: "omuz",
        type: "isolation",
        description: "Dumbbell ile yana açış (yan omuz).",
        muscles: { shoulders: 0.95, arms: 0.05 }
    },
    {
        id: "front_raise",
        name: "Front Raise",
        category: "omuz",
        type: "isolation",
        description: "Dumbbell ön omuz kaldırma.",
        muscles: { shoulders: 0.95, chest: 0.05 }
    },
    {
        id: "face_pull",
        name: "Face Pull",
        category: "omuz",
        type: "isolation",
        description: "Kabloda yüze doğru çekiş (arka omuz/trapez).",
        muscles: { shoulders: 0.8, back: 0.2 }
    },
    {
        id: "rear_delt_fly",
        name: "Rear Delt Fly",
        category: "omuz",
        type: "isolation",
        description: "Eğilerek omuz açış (arka omuz).",
        muscles: { shoulders: 0.9, back: 0.1 }
    },

    // --- BACAK (LEGS) ---
    {
        id: "squat",
        name: "Barbell Back Squat",
        category: "bacak",
        type: "compound",
        description: "Barbell sırtta çökme hareketi.",
        muscles: { legs: 0.8, core: 0.2 }
    },
    {
        id: "leg_press",
        name: "Leg Press",
        category: "bacak",
        type: "compound",
        description: "Makinede bacak presi.",
        muscles: { legs: 0.95, core: 0.05 }
    },
    {
        id: "leg_extension",
        name: "Leg Extension",
        category: "bacak",
        type: "isolation",
        description: "Oturarak ön bacak kaldırma.",
        muscles: { legs: 1.0 }
    },
    {
        id: "leg_curl",
        name: "Lying Leg Curl",
        category: "bacak",
        type: "isolation",
        description: "Yatarak arka bacak bükme.",
        muscles: { legs: 1.0 }
    },
    {
        id: "lunge",
        name: "Dumbbell Lunge",
        category: "bacak",
        type: "compound",
        description: "Adım atarak bacak çöküşü.",
        muscles: { legs: 0.9, core: 0.1 }
    },
    {
        id: "romanian_deadlift",
        name: "Romanian Deadlift (RDL)",
        category: "bacak",
        type: "compound",
        description: "Düz bacak deadlift (arka bacak/kalça).",
        muscles: { legs: 0.7, back: 0.2, core: 0.1 }
    },
    {
        id: "calf_raise",
        name: "Standing Calf Raise",
        category: "bacak",
        type: "isolation",
        description: "Ayakta kalf kaldırma.",
        muscles: { legs: 1.0 }
    },

    // --- KOL (ARMS) ---
    {
        id: "biceps_curl",
        name: "Barbell Biceps Curl",
        category: "kol",
        type: "isolation",
        description: "Barbell ile pazu bükme.",
        muscles: { arms: 1.0 } // 100% Biceps
    },
    {
        id: "dumbbell_biceps_curl",
        name: "Dumbbell Biceps Curl",
        category: "kol",
        type: "isolation",
        description: "Dumbbell ile pazu bükme.",
        muscles: { arms: 1.0 }
    },
    {
        id: "hammer_curl",
        name: "Hammer Curl",
        category: "kol",
        type: "isolation",
        description: "Nötr tutuş dumbbell pazu bükme.",
        muscles: { arms: 1.0 }
    },
    {
        id: "triceps_pushdown",
        name: "Triceps Pushdown (Kablo)",
        category: "kol",
        type: "isolation",
        description: "Kabloda aşağı itiş (arka kol).",
        muscles: { arms: 1.0 } // 100% Triceps
    },
    {
        id: "triceps_overhead_extension",
        name: "Overhead Triceps Extension",
        category: "kol",
        type: "isolation",
        description: "Baş arkasından dumbbell arka kol.",
        muscles: { arms: 1.0 }
    },
    {
        id: "close_grip_bench_press",
        name: "Close Grip Bench Press",
        category: "kol",
        type: "compound",
        description: "Dar tutuş bench press (arka kol odaklı).",
        muscles: { arms: 0.6, chest: 0.3, shoulders: 0.1 }
    },
    {
        id: "preacher_curl",
        name: "Preacher Curl",
        category: "kol",
        type: "isolation",
        description: "Sehpada pazu büküşü.",
        muscles: { arms: 1.0 }
    },

    // --- CORE (CORE) ---
    {
        id: "plank",
        name: "Plank",
        category: "core",
        type: "isolation",
        description: "Vücut ağırlığıyla statik karın duruşu.",
        muscles: { core: 1.0 }
    },
    {
        id: "crunch",
        name: "Abdominal Crunch",
        category: "core",
        type: "isolation",
        description: "Yarım mekik hareketi.",
        muscles: { core: 1.0 }
    },
    {
        id: "hanging_leg_raise",
        name: "Hanging Leg Raise",
        category: "core",
        type: "isolation",
        description: "Barfiks demirinde asılarak bacak kaldırma.",
        muscles: { core: 0.9, legs: 0.1 }
    },
    {
        id: "russian_twist",
        name: "Russian Twist",
        category: "core",
        type: "isolation",
        description: "Oturarak yan karın dönüşü.",
        muscles: { core: 1.0 }
    },
    {
        id: "cable_woodchop",
        name: "Cable Woodchop",
        category: "core",
        type: "compound",
        description: "Kabloda çapraz karın çekişi.",
        muscles: { core: 0.8, shoulders: 0.1, back: 0.1 }
    }
];
