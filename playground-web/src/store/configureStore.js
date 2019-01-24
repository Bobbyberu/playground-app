import { createStore, combineReducers } from 'redux';
import toggleModal from './reducers/modalReducer'
import addPlayground from './reducers/playgroundReducer'

const rootReducer = combineReducers({
    toggleModal: toggleModal,
    addPlayground: addPlayground,
})

export default createStore(rootReducer)
