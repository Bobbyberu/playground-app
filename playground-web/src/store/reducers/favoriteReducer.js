function toggleFavorite(state = { favoritePlaygrounds: [] } , action) {
    let nextState
    switch(action.type) {
        case 'TOGGLE_FAVORITE':
            // On crée une constante pour savoir si le playground passé dans 'action.value' est présent dans la liste des favoris
            const favoritePlaygroundIndex = state.favoritePlaygrounds.findIndex(item => item.id === action.value.id)
            if (favoritePlaygroundIndex !== -1){
                /*
                 Le playground est présent dans les favoris, on le supprime
                 La variable 'nextState' nous permet de respecter le principe d'immuable
                */
                nextState = {
                    ...state,
                    // Si l'index est différent de favoritePI on garde le playground
                    favoritePlaygrounds: state.favoritePlaygrounds.filter((item, index) => index !== favoritePlaygroundIndex)
                }
            }
            else {
                // Le playground n'est pas présent dans les favoris, on l'ajoute
                nextState = {
                    ...state,
                    // On concatene les playgrounds du state actuel avec ceux dans l'action envoyée
                    favoritePlaygrounds: [...state.favoritePlaygrounds, action.value]
                }
            }
            // Return nextState si sa valeur n'est pas à undefined sinon return state (principe d'immuable)
            return nextState || state
        default:
            return state
    }
}

export default toggleFavorite;
