import { createStore, combineReducers } from 'redux';
import toggleFavorite from './reducers/favoriteReducer';
import toggleModal from './reducers/modalReducer';
import handleUser from './reducers/userReducer';

const rootReducer = combineReducers({
    toggleFavorite: toggleFavorite,
    toggleModal: toggleModal,
    handleUser: handleUser,
})

export default createStore(rootReducer)
