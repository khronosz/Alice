import React from 'react';
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import * as IoIcons from 'react-icons/io';
import * as HiIcons from 'react-icons/hi';
import * as GiIcons from 'react-icons/gi';

export const SidebarData = [
  {
    title: 'Users',
    path: '/admin/users',
    icon: <FaIcons.FaUsersCog />,
    cName: 'nav-text'
  },
  {
    title: 'Projects',
    path: '/admin/projects',
    icon: <AiIcons.AiFillProject />,
    cName: 'nav-text'
  },
  {
    title: 'Demands',
    path: '/admin/demands',
    icon: <GiIcons.GiGavel />,
    cName: 'nav-text'
  },
  {
    title: 'Reports',
    path: '/admin/reports',
    icon: <IoIcons.IoIosPaper />,
    cName: 'nav-text'
  },
  {
    title: 'Password Reset',
    path: '/admin/passwordReset',
    icon: <AiIcons.AiFillSecurityScan />,
    cName: 'nav-text'
  },
  {
    title: 'Groups',
    path: '/admin/groups',
    icon: <HiIcons.HiUserGroup />,
    cName: 'nav-text'
  }
];
