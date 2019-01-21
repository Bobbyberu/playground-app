const initialState = {
    birthday: '',
    mail: '',
    password: '',
    username: '',
};

function handleUser(state = initialState, action) {
    let nextState;
    switch (action.type) {
        case 'SET_USER':
            // La variable 'nextState' nous permet de respecter le principe d'immuable
            nextState = {
                // On récupère l'état précédent et on initialise le user avec les infos du token dans l'action
                ...state,
                birthday: action.value[0],
                mail: action.value[1],
                password: action.value[2],
                username: action.value[3],
            }
            return nextState || state;

        case 'UPDATE_PASSWORD':
            // La variable 'nextState' nous permet de respecter le principe d'immuable
            nextState = {
                // On récupère l'état précédent et on met à jour le mdp
                ...state,
                password: action.value
            }
            return nextState || state;

        case 'UPDATE_MAIL':
            // La variable 'nextState' nous permet de respecter le principe d'immuable
            nextState = {
                // On récupère l'état précédent et on met à jour le mail
                ...state,
                mail: action.value
            }
            return nextState || state;

        default:
            return state;
    }
}

export default handleUser