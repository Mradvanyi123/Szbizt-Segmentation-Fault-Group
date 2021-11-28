import { Post } from "../structures/Post";
import { Roles, User } from "../structures/User";

export const MOCK_POSTS:Post[]=[
    {id:'0', title:'Valami', img:'img1', userName:'Pista',comments: [{userName:'Béla',text:'jó kép'}, {userName:'Jolán',text:':)))'}]},
    {id:'1',title:'Kertészet', img:'mig2', userName:'Józsi',comments: [{userName:'Jakab',text:'xD'}]},
    {id:'2',title:'asd', img:'mig3', userName:'Béla',comments: [{userName:'Anna',text:'Remek!'}]},
    {id:'3',title:'Kép', img:'mig4', userName:'Sára',comments: [{userName:'Béla',text:'asd'}]},
    {id:'4',title:'Izé', img:'mig5', userName:'Jolán',comments: []},
    {id:'5',title:'qwe', img:'mig6', userName:'Jakab',comments: []},
    {id:'6',title:'Hegyek', img:'mig7', userName:'Sarolta',comments: []},
    {id:'7',title:'Cucc', img:'mig8', userName:'Pista',comments: [{userName:'Béla',text:'jó kép'}, {userName:'Jolán',text:':)))'}]},
    {id:'8',title:'Bicikli', img:'mig9', userName:'Pista',comments: []},
    {id:'9',title:'Adjkl', img:'mig10', userName:'Béla',comments: [{userName:'Béla',text:'jó kép'}, {userName:'Jolán',text:':)))'}]},
    {id:'10',title:'Egykéthá', img:'mig11', userName:'Jolán',comments: []},
    {id:'11',title:'Poszt 11', img:'mig12', userName:'Sára',comments: []},
    {id:'12',title:'Poszt 12', img:'mig13', userName:'Anna',comments: [{userName:'Pista',text:'jó kép'}, {userName:'Sári',text:'-.-'}]},
]

export const MOCK_USERS:User[]=[
    {id:'0',username:'Jani', email:'jani@gmail.com', role:Roles.USER},
    {id:'2',username:'Pista', email:'pista@gmail.com', role:Roles.ADMIN},
    {id:'3',username:'Jakab', email:'asd@gmail.com', role:Roles.USER},
    {id:'4',username:'Béla', email:'lazac@gmail.com', role:Roles.USER},
    {id:'5',username:'Sára', email:'sdf@gmail.com', role:Roles.USER},
    {id:'6',username:'Jolán', email:'dfg@gmail.com', role:Roles.USER},
    {id:'7',username:'Anna', email:'fgh@gmail.com', role:Roles.USER},
    {id:'8',username:'Józsi', email:'ghj@gmail.com', role:Roles.USER},
    {id:'9',username:'András', email:'ert@gmail.com', role:Roles.USER},
]