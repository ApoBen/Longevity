// Realistic Demo Data Generator for Longevity Platform

export function getDemoData() {
    const today = new Date();
    const todayStr = getLocalDateString(today);
    
    const yesterday = new Date(today);
    yesterday.setDate(yesterday.getDate() - 1);
    const yesterdayStr = getLocalDateString(yesterday);

    const dayBefore = new Date(today);
    dayBefore.setDate(dayBefore.getDate() - 2);
    const dayBeforeStr = getLocalDateString(dayBefore);

    return {
        exportVersion: "2.0",
        exportDate: new Date().toISOString(),
        days: [
            // --- TODAY'S DEMO DATA ---
            {
                date: todayStr,
                energyScore: 85,
                heartRate: {
                    dailySummary: { avg: 72, min: 52, max: 145, resting: 58 },
                    hourly: [
                        { hour: 0, min: 55, max: 62, avg: 58, count: 6 },
                        { hour: 1, min: 52, max: 58, avg: 54, count: 5 },
                        { hour: 2, min: 54, max: 60, avg: 56, count: 6 },
                        { hour: 3, min: 53, max: 59, avg: 55, count: 5 },
                        { hour: 4, min: 52, max: 58, avg: 54, count: 6 },
                        { hour: 5, min: 54, max: 60, avg: 56, count: 6 },
                        { hour: 6, min: 58, max: 72, avg: 65, count: 4 },
                        { hour: 7, min: 68, max: 85, avg: 76, count: 8 },
                        { hour: 8, min: 72, max: 110, avg: 88, count: 12 },
                        { hour: 9, min: 65, max: 95, avg: 78, count: 10 },
                        { hour: 10, min: 62, max: 80, avg: 71, count: 8 },
                        { hour: 11, min: 60, max: 78, avg: 68, count: 8 },
                        { hour: 12, min: 64, max: 88, avg: 73, count: 9 },
                        { hour: 13, min: 62, max: 85, avg: 70, count: 9 },
                        { hour: 14, min: 58, max: 82, avg: 67, count: 7 },
                        { hour: 15, min: 60, max: 80, avg: 69, count: 7 },
                        { hour: 16, min: 50, max: 80, avg: 65, count: 7 },
                        { hour: 17, min: 88, max: 145, avg: 120, count: 15 },
                        { hour: 18, min: 75, max: 110, avg: 85, count: 10 },
                        { hour: 19, min: 68, max: 88, avg: 74, count: 8 },
                        { hour: 20, min: 65, max: 82, avg: 71, count: 8 },
                        { hour: 21, min: 62, max: 78, avg: 67, count: 7 },
                        { hour: 22, min: 58, max: 70, avg: 63, count: 6 },
                        { hour: 23, min: 56, max: 65, avg: 60, count: 6 }
                    ]
                },
                bloodOxygen: {
                    hourly: [
                        { hour: 0, avg: 98.2, min: 97.0, count: 3 },
                        { hour: 1, avg: 98.5, min: 97.5, count: 3 },
                        { hour: 2, avg: 97.8, min: 96.8, count: 4 },
                        { hour: 3, avg: 98.5, min: 97.8, count: 2 },
                        { hour: 4, avg: 99.0, min: 98.0, count: 3 },
                        { hour: 5, avg: 98.6, min: 97.2, count: 3 },
                        { hour: 8, avg: 97.2, min: 96.9, count: 1 },
                        { hour: 12, avg: 98.8, min: 98.0, count: 2 },
                        { hour: 15, avg: 99.1, min: 99.1, count: 1 },
                        { hour: 18, avg: 98.4, min: 97.5, count: 2 }
                    ]
                },
                steps: {
                    total: 8542,
                    goal: 10000,
                    distanceMeters: 6200.0,
                    hourly: [
                        { hour: 7, steps: 1200 },
                        { hour: 8, steps: 850 },
                        { hour: 9, steps: 320 },
                        { hour: 12, steps: 3000 },
                        { hour: 13, steps: 1500 },
                        { hour: 17, steps: 1672 }
                    ]
                },
                skinTemperature: {
                    hourly: [
                        { hour: 0, avg: 35.8, count: 1 },
                        { hour: 1, avg: 36.1, count: 1 },
                        { hour: 2, avg: 36.4, count: 1 },
                        { hour: 3, avg: 36.3, count: 1 },
                        { hour: 4, avg: 36.2, count: 1 },
                        { hour: 5, avg: 36.2, count: 1 },
                        { hour: 14, avg: 36.7, count: 1 },
                        { hour: 20, avg: 36.5, count: 1 }
                    ]
                },
                sleep: {
                    totalMinutes: 452, // 7 hours 32 min
                    score: 92,
                    startTime: new Date(new Date(today).setHours(-1, 15, 0, 0)).toISOString(), // 23:15 yesterday
                    endTime: new Date(new Date(today).setHours(6, 47, 0, 0)).toISOString(), // 06:47 today
                    stages: { rem: 105, light: 192, deep: 115, awake: 40 }
                },
                calories: {
                    total: 2145.0,
                    active: 645.0,
                    rest: 1500.0
                },
                bloodPressure: [
                    { systolic: 120, diastolic: 80, pulse: 72, time: "09:00" }
                ],
                bloodGlucose: [
                    { glucose: 95, mealType: "FASTING", time: "07:00" }
                ],
                bodyComposition: {
                    weightKg: 75.2, bodyFat: 18.5, muscleMass: 35.2, bmi: 24.1
                },
                nutrition: {
                    calories: 1850, carbs: 220, protein: 85, fat: 65, fiber: 28
                },
                waterIntake: { amountMl: 2100, goalMl: 2500 },
                floors: { climbed: 12, goal: 10 },
                workouts: [
                    {
                        type: "Running", durationMin: 35, calories: 320,
                        start: "07:00", end: "07:35", avgHR: 142, distanceM: 5200
                    }
                ]
            },
            // --- YESTERDAY'S DEMO DATA ---
            {
                date: yesterdayStr,
                energyScore: 78,
                heartRate: {
                    dailySummary: { avg: 75, min: 54, max: 160, resting: 60 },
                    hourly: [
                        { hour: 0, min: 58, max: 65, avg: 60, count: 6 },
                        { hour: 1, min: 55, max: 62, avg: 57, count: 5 },
                        { hour: 2, min: 54, max: 60, avg: 56, count: 6 },
                        { hour: 3, min: 55, max: 60, avg: 57, count: 5 },
                        { hour: 4, min: 56, max: 61, avg: 58, count: 6 },
                        { hour: 5, min: 57, max: 62, avg: 59, count: 6 },
                        { hour: 6, min: 62, max: 80, avg: 70, count: 4 },
                        { hour: 7, min: 70, max: 90, avg: 80, count: 8 },
                        { hour: 8, min: 75, max: 120, avg: 95, count: 12 },
                        { hour: 9, min: 68, max: 100, avg: 82, count: 10 },
                        { hour: 10, min: 65, max: 85, avg: 74, count: 8 },
                        { hour: 11, min: 62, max: 80, avg: 70, count: 8 },
                        { hour: 12, min: 66, max: 92, avg: 76, count: 9 },
                        { hour: 13, min: 64, max: 88, avg: 73, count: 9 },
                        { hour: 14, min: 60, max: 84, avg: 70, count: 7 },
                        { hour: 15, min: 62, max: 82, avg: 71, count: 7 },
                        { hour: 16, min: 64, max: 90, avg: 75, count: 7 },
                        { hour: 17, min: 95, max: 160, avg: 132, count: 15 },
                        { hour: 18, min: 80, max: 120, avg: 92, count: 10 },
                        { hour: 19, min: 72, max: 95, avg: 78, count: 8 },
                        { hour: 20, min: 68, max: 88, avg: 75, count: 8 },
                        { hour: 21, min: 64, max: 82, avg: 70, count: 7 },
                        { hour: 22, min: 60, max: 75, avg: 65, count: 6 },
                        { hour: 23, min: 58, max: 68, avg: 62, count: 6 }
                    ]
                },
                bloodOxygen: {
                    hourly: [
                        { hour: 0, avg: 98.0, min: 97.0, count: 3 },
                        { hour: 1, avg: 98.2, min: 97.2, count: 3 },
                        { hour: 2, avg: 97.6, min: 96.5, count: 4 },
                        { hour: 3, avg: 98.2, min: 97.0, count: 2 },
                        { hour: 4, avg: 98.8, min: 97.8, count: 3 },
                        { hour: 5, avg: 98.4, min: 97.0, count: 3 }
                    ]
                },
                steps: {
                    total: 12430,
                    goal: 10000,
                    distanceMeters: 9100.0,
                    hourly: [
                        { hour: 7, steps: 1500 },
                        { hour: 8, steps: 1250 },
                        { hour: 10, steps: 800 },
                        { hour: 12, steps: 2200 },
                        { hour: 14, steps: 1100 },
                        { hour: 17, steps: 4500 },
                        { hour: 18, steps: 1080 }
                    ]
                },
                skinTemperature: {
                    hourly: [
                        { hour: 0, avg: 35.9, count: 1 },
                        { hour: 14, avg: 36.5, count: 1 }
                    ]
                },
                sleep: {
                    totalMinutes: 388, // 6 hours 28 min
                    score: 80,
                    startTime: new Date(new Date(yesterday).setHours(-1, 45, 0, 0)).toISOString(),
                    endTime: new Date(new Date(yesterday).setHours(5, 13, 0, 0)).toISOString(),
                    stages: { rem: 80, light: 188, deep: 90, awake: 30 }
                },
                calories: {
                    total: 2450.0,
                    active: 950.0,
                    rest: 1500.0
                },
                workouts: [
                    {
                        type: "Running", durationMin: 50, calories: 510,
                        start: "17:00", end: "17:50", avgHR: 148, distanceM: 7800
                    }
                ]
            },
            // --- DAY BEFORE'S DEMO DATA ---
            {
                date: dayBeforeStr,
                energyScore: 92,
                heartRate: {
                    dailySummary: { avg: 68, min: 50, max: 130, resting: 55 },
                    hourly: [
                        { hour: 0, min: 52, max: 58, avg: 55, count: 6 },
                        { hour: 1, min: 50, max: 56, avg: 53, count: 5 },
                        { hour: 2, min: 52, max: 57, avg: 54, count: 6 }
                    ]
                },
                bloodOxygen: {
                    hourly: [
                        { hour: 0, avg: 99.1, min: 98.0, count: 3 }
                    ]
                },
                steps: {
                    total: 6120,
                    goal: 10000,
                    distanceMeters: 4400.0,
                    hourly: [
                        { hour: 8, steps: 1120 },
                        { hour: 12, steps: 1500 },
                        { hour: 15, steps: 2000 },
                        { hour: 19, steps: 1500 }
                    ]
                },
                sleep: {
                    totalMinutes: 502, // 8 hours 22 min
                    score: 96,
                    startTime: new Date(new Date(dayBefore).setHours(-1, 0, 0, 0)).toISOString(),
                    endTime: new Date(new Date(dayBefore).setHours(7, 22, 0, 0)).toISOString(),
                    stages: { rem: 120, light: 220, deep: 130, awake: 32 }
                },
                calories: {
                    total: 1950.0,
                    active: 450.0,
                    rest: 1500.0
                }
            }
        ]
    };
}

function getLocalDateString(date) {
    const offset = date.getTimezoneOffset();
    const localDate = new Date(date.getTime() - (offset * 60 * 1000));
    return localDate.toISOString().split('T')[0];
}
