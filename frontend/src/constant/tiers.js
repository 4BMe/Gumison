export default {
    // tiers:{
    //     브론즈4: 'bronze4',
    //     브론즈3: 'bronze3',
    //     브론즈2: 'bronze2',
    //     브론즈1: 'bronze1',
    //     실버4: 'silver4',
    //     실버3: 'silver3',
    //     실버2: 'silver2',
    //     실버1: 'silver1',
    //     골드4: 'gold4',
    //     골드3: 'gold3',
    //     골드2: 'gold2',
    //     골드1: 'gold1',
    //     플레티넘4: 'platinum4',
    //     플레티넘3: 'platinum3',
    //     플레티넘2: 'platinum2',
    //     플레티넘1: 'platinum1',
    //     다이아4: 'diamond4',
    //     다이아3: 'diamond3',
    //     다이아2: 'diamond2',
    //     다이아1: 'diamond1',
    //     마스터4: 'master4',
    //     마스터3: 'master3',
    //     마스터2: 'master2',
    //     마스터1: 'master1',
    // },
    tierDown(tier) {
        if(tier[tier.length - 1] != 4) {
            return tier.slice(0, tier.length-1) + (parseInt(tier[tier.length-1], 10) + 1)
        } else {
            switch(tier) {
                case 'master4':
                    return 'diamond1';
                case 'diamond4':
                    return 'platinum1';
                case 'platinum4':
                    return 'gold1';
                case 'gold4':
                    return 'silver1'; 
                case 'silver4':
                    return 'bronze1';
            }
        }
        return tier;
    },
    tierUp(tier) {
        if(tier[tier.length - 1] != 1) {
            return tier.slice(0, tier.length-1) + (parseInt(tier[tier.length-1], 10) - 1)
        } else {
            switch(tier) {
                case 'diamond1':
                    return 'master4';
                case 'platinum1':
                    return 'diamond4';
                case 'gold1':
                    return 'platinum4'; 
                case 'silver1':
                    return 'gold4';
                case 'bronze1':
                    return 'silver4';
            }
        }
        return tier;
    }
}