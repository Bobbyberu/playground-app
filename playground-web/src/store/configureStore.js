import { createStore, combineReducers } from 'redux';
import toggleModal from './reducers/modalReducer';
import handleUser from './reducers/userReducer';
import toggleModal from './reducers/modalReducer'
import addPlayground from './reducers/playgroundReducer'

const rootReducer = combineReducers({
    toggleModal: toggleModal,
    handleUser: handleUser,
    addPlayground: addPlayground,
})

export default createStore(rootReducer)
