import { createStore, combineReducers } from 'redux';
<<<<<<< HEAD
import toggleFavorite from './reducers/favoriteReducer'
import toggleModal from './reducers/modalReducer'
import addPlayground from './reducers/playgroundReducer'
=======
import toggleModal from './reducers/modalReducer';
>>>>>>> develop

const rootReducer = combineReducers({
    toggleModal: toggleModal,
    addPlayground: addPlayground,
})

export default createStore(rootReducer)
