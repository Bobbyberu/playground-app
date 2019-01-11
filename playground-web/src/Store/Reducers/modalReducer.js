const initialState = { open: false }

function toggleModal(state = initialState , action) {
    let nextState
    switch(action.type) {
        case 'TOGGLE_STATE_MODAL':
            nextState = {
                ...state,
                open : action.value
            }
            console.log("nextstate : " + nextState)
            return nextState || state
        default:
            return state
    }
}

export default toggleModal