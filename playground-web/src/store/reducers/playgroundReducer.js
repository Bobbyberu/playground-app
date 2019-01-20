const initialState = {
    name: '',
    street: '',
    cp: '',
    city: '',
    averageMark: 0,
    city: '',
    covered: false,
    description: '',
    image: null,
    latitude: 0,
    longitude: 0,
    name: '',
    players: [],
    private: false,
    sports: [],
    surface: null,
    step: 0,
}

function addPlayground(state = initialState, action) {
    let nextState
    switch (action.type) {
        case 'SET_STEP':
            // Définir l'étape courante du steppeur
            nextState = {
                ...state,
                step: action.value
            }
            // Return nextState si sa valeur n'est pas à undefined sinon return state (principe d'immuable)
            return nextState || state

        case 'ADD_ADDRESS':
            // Définir l'adresse du playground (rue , code postal et ville)
            nextState = {
                ...state,
                street: action.value[0],
                cp: action.value[1],
                city: action.value[2],
            }
            return nextState || state

        case 'SET_ACCESS':
            // Définir la privacité du terrain
            nextState = {
                ...state,
                private: action.value
            }
            return nextState || state

        case 'SET_SPORTS':
            // Définir les sports pratiqués sur le terrain
            nextState = {
                ...state,
                sports: action.value
            }
            return nextState || state

        case 'SET_DESCRIPTION':
            // Ajouter une description au terrain
            nextState = {
                ...state,
                description: action.value
            }
            return nextState || state

        case 'SET_NAME':
            // Ajouter une description au terrain
            nextState = {
                ...state,
                name: action.value
            }
            return nextState || state

        case 'RESET':
            // Réinitialiser tous les champs
            nextState = { ...initialState }
            return nextState

        default:
            return state
    }
}

export default addPlayground