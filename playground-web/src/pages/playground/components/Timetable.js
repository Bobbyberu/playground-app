import React from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

import { withStyles } from '@material-ui/core/styles';
import red from '@material-ui/core/colors/red';

const styles = {
    closed: {
        color: red[500]
    },
    footer: {
        color: '#000'
    }
};

class Timetable extends React.Component {
    renderTimeRow(day, index) {
        const { classes } = this.props;
        let variant = index === 6 ? 'footer' : 'body';

        return (
            <TableRow key={index}>
                <TableCell classes={{ footer: classes.footer }} variant={variant}>{day.day}</TableCell>
                <TableCell classes={{ footer: classes.footer }} variant={variant}>
                    <span className={day.opening !== null ? '' : classes.closed}>
                        {day.opening !== null ? day.opening : 'Fermé'}
                    </span>
                </TableCell>
                <TableCell classes={{ footer: classes.footer }} variant={variant}>
                    <span className={day.opening !== null ? '' : classes.closed}>
                        {day.closing !== null ? day.closing : 'Fermé'}
                    </span>
                </TableCell>
            </TableRow >
        );
    }

    renderAllDays() {
        let timetable = this.props.timetable;
        return (
            <React.Fragment>
                {timetable.map((day, index) => (this.renderTimeRow(day, index)))}
            </React.Fragment>
        );
    }

    render() {
        return (
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Jour</TableCell>
                        <TableCell>Ouverture</TableCell>
                        <TableCell>Fermeture</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {this.renderAllDays()}
                </TableBody>
            </Table>
        );
    }
}

export default withStyles(styles)(Timetable);