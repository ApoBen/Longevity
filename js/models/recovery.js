export function calculateRecovery(workouts) {
    const muscleGroups = {
        'Chest': 100,
        'Back': 100,
        'Legs': 100,
        'Shoulders': 100,
        'Arms': 100,
        'Core': 100
    };

    const now = new Date();
    const msPerDay = 1000 * 60 * 60 * 24;

    workouts.forEach(w => {
        const wDate = new Date(w.date);
        const daysAgo = Math.max(0, (now - wDate) / msPerDay);
        
        // Only care about last 5 days
        if (daysAgo <= 5) {
            w.exercises.forEach(ex => {
                let cat = ex.category;
                
                // Map sub-categories if needed, for now exact match
                if (muscleGroups[cat] !== undefined) {
                    let totalFatigue = 0;
                    ex.sets.forEach(set => {
                        // 1 set of RPE 8 adds ~4 fatigue points
                        totalFatigue += (set.rpe || 8) * 0.5;
                    });

                    // Fatigue decays over time. Assume 15 points recovery per day
                    const remainingFatigue = Math.max(0, totalFatigue - (daysAgo * 15));
                    muscleGroups[cat] = Math.max(0, muscleGroups[cat] - remainingFatigue);
                }
            });
        }
    });

    return muscleGroups;
}

export function checkWorkoutConflict(currentWorkoutExercises, recoveryStatus) {
    const conflicts = [];
    currentWorkoutExercises.forEach(ex => {
        if (recoveryStatus[ex.category] !== undefined) {
            if (recoveryStatus[ex.category] < 40) {
                conflicts.push(`Kas grubu (${ex.category}) henüz tam toparlanmadı (Toparlanma: %${Math.round(recoveryStatus[ex.category])}).`);
            }
        }
    });
    return conflicts;
}
