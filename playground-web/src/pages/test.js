import React from 'react';
import PlaygroundAPI from '../services/playground-api';

export default class Test extends React.Component {
    constructor(props) {
        super(props);

        this.state = ({
            selectedFile: null,
            selectedFilePlayground: null,
        })

        this.api = new PlaygroundAPI();
    }

    handleSelectedFile = event => {
        this.setState({
            selectedFile: event.target.files[0],
            loaded: 0
        });
    }

    handleSubmit = event => {
        event.preventDefault();

        let data = new FormData();
        data.append('file', this.state.selectedFile, this.state.selectedFile.name);
        //let data = { 'file': this.state.selectedFile, 'type': 'user' }

        //console.log(data.get("file"));
        this.api.uploadImageUser(data);
    }

    handleSelectedFilePlayground = event => {
        this.setState({
            selectedFilePlayground: event.target.files[0],
            loaded: 0
        });
    }

    handleSubmitPlayground = event => {
        event.preventDefault();

        let data = new FormData();
        data.append('file', this.state.selectedFilePlayground, this.state.selectedFilePlayground.name);

        this.api.uploadImagePlayground(1, data);
    }

    render() {
        return (
            <React.Fragment>
                <div>
                    <h2>Upload file</h2>
                </div>

                <div>
                    <form encType="multipart/form-data" action="">
                        <table>
                            <tr><td>File to upload:</td><td>
                                <input type="file" name="file" onChange={this.handleSelectedFile} />
                            </td></tr>
                            <tr><td>
                                <button onClick={this.handleSubmit}>Upload</button>
                            </td></tr>
                        </table>
                        <table>
                            <tr><td>File to upload for playground:</td><td>
                                <input type="file" name="file" onChange={this.handleSelectedFilePlayground} />
                            </td></tr>
                            <tr><td>
                                <button onClick={this.handleSubmitPlayground}>Upload</button>
                            </td></tr>
                        </table>
                    </form>
                </div>
                <div>
                    <img crossOrigin="true" src={this.api.getUserImage()} />
                    <img crossOrigin="true" src={this.api.getPlaygroundImage(1)} />
                </div>
            </React.Fragment>
        );
    }
} 