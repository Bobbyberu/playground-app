import React from 'react';
import { Redirect, Link } from 'react-router-dom';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import { fade } from '@material-ui/core/styles/colorManipulator';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

import AuthService from '../../services/auth';
import Navbar from '../../common-components/nav-bar/nav-bar';
import PlaygroundAPI from '../../services/playground-api';

import Delete from '@material-ui/icons/Delete';
import green from '@material-ui/core/colors/green';
import grey from '@material-ui/core/colors/grey';

const theme = createMuiTheme({
  palette: {
    primary: {
      main: green[500],
    },
    secondary: {
      main: grey[50],
    },
  }
});

const styles = ({
  global: {
    height: '100%'
  },
  card: {
    width: 1400,
    marginRight: 'auto',
    marginLeft: 'auto',
    height: '100%',
    borderRadius: 0
  },
  cardContent: {
    paddingLeft: 40,
    paddingRight: 40
  },
  sectionTitle: {
    textAlign: 'left',
    marginTop: 40,
    marginBottom: 30
  },
  link: {
    color: fade(green[500], 1),
    '&:hover': {
      textDecoration: 'none',
      color: fade(green[500], 0.8),
    },
  },
});

class PlaygroundReports extends React.Component {
  state = ({
    reports: []
  });

  componentDidMount() {
    PlaygroundAPI.getAllPlaygroundReports()
      .then(response => {
        this.setState({
          reports: response
        });
      })
      .catch(err => console.log(err))
  }

  deleteReport = (report) => {
    let confirm = window.confirm("Voulez-vous vraiment supprimer ce signalement ?");
    if (confirm) {
      PlaygroundAPI.deletePlaygroundReport(report.id)
        .then(() => {
          let allReports = this.state.reports;
          allReports = allReports.filter(item => item !== report);

          this.setState({
            reports: allReports
          });
        })
        .catch(err => console.log(err));
    }
  }

  render() {
    if (!AuthService.isAdmin()) {
      return (<Redirect to="/" />);
    } else {
      const { classes } = this.props;
      return (
        <MuiThemeProvider theme={theme}>
          <Navbar searchbar={false} />
          <div className={classes.global}>
            <Card className={classes.card}>
              <CardContent className={classes.cardContent}>
                <div className={classes.sectionTitle}><h5><span aria-label="clock" role="img">🏴</span>Signalements de playgrounds</h5></div>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Auteur</TableCell>
                      <TableCell>Playground</TableCell>
                      <TableCell>Raison</TableCell>
                      <TableCell></TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {this.state.reports.map(report => (
                      this.renderRow(report)
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </div>
        </MuiThemeProvider>
      );
    }
  }

  renderRow(report) {
    const { classes } = this.props;
    return (
      <TableRow key={report.id}>
        <TableCell>{report.author.username}</TableCell>
        <TableCell>
          <Link className={classes.link} to={'/details/' + report.playground.id}>
            {report.playground.name}
          </Link>
        </TableCell>
        <TableCell>{report.description}</TableCell>
        <TableCell>
          <IconButton color="primary" onClick={() => this.deleteReport(report)}>
            <Delete />
          </IconButton>
        </TableCell>
      </TableRow>
    );
  }
}

export default withStyles(styles)(PlaygroundReports);