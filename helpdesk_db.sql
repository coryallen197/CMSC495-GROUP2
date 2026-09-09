-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 10, 2026 at 01:09 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `helpdesk_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `priority` varchar(20) DEFAULT 'Medium',
  `status` varchar(20) DEFAULT 'Open',
  `assigned_to` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `ticket_title` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`ticket_id`, `user_id`, `title`, `description`, `category`, `priority`, `status`, `assigned_to`, `created_at`, `ticket_title`, `created_date`, `updated_date`) VALUES
(1, 1, 'New Case', 'They had a problem', 'Password/Login', 'Low', 'Open', NULL, '2026-09-02 18:31:35', '', '2026-09-07 22:22:51', '2026-09-07 22:23:26'),
(2, 1, 'New Ticket', 'Cannot connect to wifi', 'Software', 'Medium', 'Closed', NULL, '2026-09-02 18:56:12', '', '2026-09-07 22:22:51', '2026-09-09 22:50:26'),
(3, 1, '555', '555', 'Hardware', 'Low', 'Open', NULL, '2026-09-07 22:40:34', '555', '2026-09-07 22:40:34', '2026-09-07 22:40:34'),
(4, 1, 'help', 'We need help', 'Hardware', 'Low', 'Open', NULL, '2026-09-09 22:39:52', 'help', '2026-09-09 22:39:52', '2026-09-09 22:39:52');

-- --------------------------------------------------------

--
-- Table structure for table `ticket_history`
--

CREATE TABLE `ticket_history` (
  `history_id` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `action` varchar(255) NOT NULL,
  `old_status` varchar(50) DEFAULT NULL,
  `new_status` varchar(50) DEFAULT NULL,
  `changed_by` varchar(100) DEFAULT NULL,
  `change_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ticket_history`
--

INSERT INTO `ticket_history` (`history_id`, `ticket_id`, `action`, `old_status`, `new_status`, `changed_by`, `change_date`) VALUES
(1, 1, 'Ticket Created', NULL, 'Open', 'Richard', '2026-09-07 20:05:09'),
(2, 3, 'Ticket Created', NULL, 'Open', 'System', '2026-09-07 22:40:34'),
(3, 4, 'Ticket Created', NULL, 'Open', 'System', '2026-09-09 22:39:52'),
(4, 2, 'Status Changed', 'Open', 'Closed', 'System', '2026-09-09 22:50:26');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `name`, `email`, `password`, `role`) VALUES
(1, 'Test User', 'test@example.com', 'temporarypassword', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `ticket_history`
--
ALTER TABLE `ticket_history`
  ADD PRIMARY KEY (`history_id`),
  ADD KEY `ticket_id` (`ticket_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tickets`
--
ALTER TABLE `tickets`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `ticket_history`
--
ALTER TABLE `ticket_history`
  MODIFY `history_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `ticket_history`
--
ALTER TABLE `ticket_history`
  ADD CONSTRAINT `ticket_history_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`ticket_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
