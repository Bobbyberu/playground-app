import { createStore, combineReducers } from 'redux';
import toggleFavorite from './reducers/favoriteReducer'
import toggleModal from './reducers/modalReducer'
import addPlayground from './reducers/playgroundReducer'

const rootReducer = combineReducers({
    toggleFavorite: toggleFavorite,
    toggleModal: toggleModal,
    addPlayground: addPlayground,
})

export default createStore(rootReducer)
