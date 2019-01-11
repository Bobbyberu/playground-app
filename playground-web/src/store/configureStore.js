import { createStore, combineReducers } from 'redux';
import toggleFavorite from './favoriteReducer'
import toggleModal from './modalReducer'

const rootReducer = combineReducers({
    toggleFavorite: toggleFavorite,
    toggleModal: toggleModal,
})

export default createStore(rootReducer)