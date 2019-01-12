import { createStore, combineReducers } from 'redux';
import toggleFavorite from './reducers/favoriteReducer'
import toggleModal from './reducers/modalReducer'

const rootReducer = combineReducers({
    toggleFavorite: toggleFavorite,
    toggleModal: toggleModal,
})

export default createStore(rootReducer)