const initialState = { open: false }

function toggleModal(state = initialState , action) {
    let nextState
    switch(action.type) {
        case 'TOGGLE_STATE_MODAL':
        // la variable nextState prend la valeur reçue dans l'action (true ou false)
            nextState = {
                ...state,
                open : action.value
            }
            // Return nextState si sa valeur n'est pas à undefined sinon return state (principe d'immuable)
            return nextState || state
        default:
            return state
    }
}

export default toggleModal